package com.stefan.myLock;

import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MCSLock extends SpinLock{
    class Node {
        volatile Node next;
        //false代表未持有锁，true代表持有锁
        volatile boolean locked = false;
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
        h.setLocked(false);
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

    protected boolean tryAcquire(int acquires) {
        Node node = threadNode.get();
        int c = getState();
        int nextc = c + acquires;
        if (node != null && getExclusiveOwnerThread() != null && node.getThread() == getExclusiveOwnerThread()) {
            /**
             * 一般情况node != null，说明有同一个线程已经调用了lock()
             * 持有锁的线程是node.thread，重入
             */
            setState(nextc);
            return true;
        }
        node = enq();
        if (!node.shouldLocked()) {
            //判断node是否应该获取锁，若node.locked=true，代表应该获取锁。则结束自旋
            setState(nextc);
            setExclusiveOwnerThread(node.getThread());
            return true;
        }
        return false;
    }

    protected boolean tryRelease(int releases) {
        int c = getState() - releases;
        final Thread current = Thread.currentThread();
        if (current != getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException();
        }

        boolean free = false;
        Node node = threadNode.get();
        //在node.setLocked(false) 之前设置 state
        setState(c < 0 ? 0 : c);
        //完全释放锁，将前驱 locked改为false，让其后继感知锁空闲并停止自旋
        if (c <= 0) {
            free = true;
            setExclusiveOwnerThread(null);
            notifySuccessor();
        }
        return free;
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
    private static final Unsafe unsafe = getUnsafe();
    private static final long tailOffset;

    static {
        try {
            tailOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));

        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
