package com.stefan.myLock;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock {
    class Node {
        volatile Node next;
        //false代表未持有锁，true代表持有锁
        volatile boolean locked = false;
        volatile Thread thread;
        Node(Thread thread) {
            this.thread = thread;
        }

        public boolean shouldLocked() {
            return tail.get() == this || isLocked();
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

        @Override
        public String toString() {
            return "Node{" +
                    "next=" + next +
                    ", locked=" + locked +
                    ", thread=" + thread +
                    '}';
        }
    }
    private volatile AtomicReference<Node> tail = new AtomicReference<Node>();
    private ThreadLocal<Node> threadNode = new ThreadLocal<Node>();
    private volatile Thread exclusiveOwnerThread;
    protected volatile int state = 0;


    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
    /**
     * 主动通知后继获取锁
     */
    final void notifySuccessor() {
        Node node = threadNode.get();
        while (node.successor() == null) {
            Node t = tail.get();
            if (tail.compareAndSet(t, null)) {
                //设置 tail为 null,threadNode remove
                threadNode.remove();
                return;
            }
        }
        //threadNode 后继不为空 设置后继的locked=true，主动通知后继获取锁
        setExclusiveOwnerThread(null);
        node.successor().setLocked(true);
        node = null;
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
            Node t = tail.get();
            if (tail.compareAndSet(t, node)) {
                if (t != null) {
                    t.next = node;
                }
                return node;
            }
        }
    }

    public void lock() {
        Node node = threadNode.get();
        if (node != null && getExclusiveOwnerThread() != null && node.getThread() == getExclusiveOwnerThread()) {
            /**
             * 一般情况node != null，说明有同一个线程已经调用了lock()
             * 持有锁的线程是node.thread，重入
             */
            state++;
            System.out.println("re lock thread=" + node.getThread().getId() + "state=" + state);

            return;
        }
        node = enq();
//        System.out.println(String.format("lock thread=%d;locked=%b;state=%d;", node.getThread().getId(), node.locked, state));
        System.out.println("lock next=" + node.next);
        while (!node.shouldLocked()) {}
        //判断node是否应该获取锁，若node.locked=true，代表应该获取锁。则结束自旋
        state++;
        setExclusiveOwnerThread(node.getThread());
        System.out.println("mcs get lock ok, thread=" + node.getThread().getId());
    }

    public void unlock() {
        Node node = threadNode.get();
        if (node.getThread() != getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException();
        }
        //在node.setLocked(false) 之前设置 state
        state--;
        //完全释放锁，将前驱 locked改为false，让其后继感知锁空闲并停止自旋
        if (state == 0) {
            System.out.println("un lock," + node);
            while (node.successor() == null) {
                Node t = tail.get();
                if (tail.compareAndSet(t, null)) {
                    //设置 tail为 null,threadNode remove
                    System.out.println(".....");
                    threadNode.remove();
//                    System.out.println("mcs un lock ok, thread=" + node.getThread().getId());
                    return;
                }
            }
            //threadNode 后继不为空 设置后继的locked=true，主动通知后继获取锁
            setExclusiveOwnerThread(null);
            node.successor().setLocked(true);
            threadNode.remove();
//            System.out.println("mcs un lock ok, thread=" + node.getThread().getId());
//            System.out.println("mcs un lock ok,next=" + node.next);
            node = null;
        }
    }
}
