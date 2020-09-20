package com.stefan.myLock;

import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 公平锁，FIFO，在队列头部的持有锁
 * 网上有些CLH是不可重入的，但是这个是可以重入
 */
public class CLHLock extends SpinLock{
    class Node {
        volatile Node prev;
        //true表示正在持有锁，或者需要锁
        //false代表释放锁
        private boolean locked = true;
        volatile Thread thread;
        Node(Thread thread) {
            this.thread = thread;
        }

        void setLocked(boolean locked) {
            this.locked = locked;
        }

        boolean isPrevLocked() {
            return  predecessor() != null && predecessor().locked;
        }
        final Node predecessor() {
            return prev;
        }
        Thread getThread() {
            return thread;
        }
        void setThread(Thread thread) {
            this.thread = thread;
        }
    }

    private volatile Node tail = null;
    private ThreadLocal<Node> threadNode = new ThreadLocal<Node>();
        /**
         * cas自旋入队列->尾部
         * @return
         */
    Node enq() {
        Node node = new Node(Thread.currentThread());
        threadNode.set(node);
        for (;;) {
            Node prev = tail;
            //cas设置tail指针指向node
            if (compareAndSetTail(prev, node)) {
                node.prev = prev;
                return node;
            }
        }
    }

    /**
     * 因为互斥，所以setState和setExclusiveOwnerThread是安全的
     * @param acquires
     * @return
     */
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
        if (!node.isPrevLocked()) {
            //前驱未持有锁，说明可以获取锁，即获取锁成功, prev设置为null，断开与链表的连接，相当于出队列
            setExclusiveOwnerThread(node.getThread());
            setState(nextc);
            node.prev = null;
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
        setState(c<0 ? 0 : c);
        //完全释放锁，将前驱 locked改为false，让其后继感知锁空闲并停止自旋
        if (c <= 0) {
            free = true;
            node.setLocked(false);
            setExclusiveOwnerThread(null);
            threadNode.remove();
        }
        return free;
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
    private static final Unsafe unsafe = Unsafe.getUnsafe();
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
