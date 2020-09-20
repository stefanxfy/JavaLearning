package com.stefan.myLock;

import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MCSLock {
    class Node {
        volatile Node next;
        //false代表未持有锁，true代表持有锁
        private boolean locked = false;
        volatile Thread thread;
        Node(Thread thread) {
            this.thread = thread;
        }

        public boolean shouldLocked() {
            return isLocked();
        }

        void setLocked(boolean locked) {
            this.locked = locked;
        }

        public boolean isLocked() {
            return locked;
        }

        final Node successor() {
            return next;
        }
        Thread getThread() {
            return thread;
        }

        void setNext(Node next) {
            this.next = next;
        }

        void setThread(Thread thread) {
            this.thread = thread;
        }
    }
    private volatile Node tail = null;
    private ThreadLocal<Node> threadNode = new ThreadLocal<Node>();

    /**
     * 主动通知后继获取锁
     */
    final void notifySuccessor() {
        Node h = threadNode.get();
        while (h.successor() == null) {
            Node t = tail;
            if (compareAndSetTail(t, null)) {
                //设置 tail为 null,threadNode remove
                threadNode.remove();
                return;
            }
        }
        //threadNode 后继不为空 设置后继的locked=true，主动通知后继获取锁
        h.successor().setLocked(true);
        h.setNext(null);
        threadNode.remove();
    }
    /**
     * cas自旋入队列->尾部
     * @return
     */
    Node enq() {
        Node node = new Node(Thread.currentThread());
        threadNode.set(node);
        for (;;) {
            Node t = tail;
            if (compareAndSetTail(t, node)) {
                if (t != null) {
                    t.next = node;
                }
                return node;
            }
        }
    }
    public void lock() {
        Node node = enq();
        for (;;) {
            if (node.shouldLocked()) {
                //判断node是否应该获取锁，若node.locked=true，代表应该获取锁。则结束自旋
                return;
            }
        }
    }
    public void unlock() {
        Node node = threadNode.get();
        if (!node.isLocked()) {
            return;
        }
        notifySuccessor();
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long tailOffset;
    private static final long headOffset;

    static {
        try {
            headOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));

        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
