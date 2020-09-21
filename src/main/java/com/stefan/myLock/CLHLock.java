package com.stefan.myLock;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 公平锁，FIFO，在队列头部的持有锁
 * 网上有些CLH是不可重入的，但是这个是可以重入
 */
public class CLHLock{
    class Node {
        volatile Node prev;
        //true表示正在持有锁，或者需要锁
        //false代表释放锁
        volatile boolean locked = true;
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
         * cas自旋入队列->尾部
         * @return
         */
    Node enq() {
        Node node = new Node(Thread.currentThread());
        threadNode.set(node);
        for (;;) {
            Node prev = tail.get();
            //cas设置tail指针指向node
            if (tail.compareAndSet(prev, node)) {
                node.prev = prev;
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
        while (node.isPrevLocked()) {

        }
        //前驱未持有锁，说明可以获取锁，即获取锁成功, prev设置为null，断开与链表的连接，相当于前驱出队列
        setExclusiveOwnerThread(node.getThread());
        state++;
        node.prev = null;
        System.out.println("clh get lock ok, thread=" + node.getThread().getId());
    }

    public void unlock() {
        Node node = threadNode.get();
        if (node.getThread() != getExclusiveOwnerThread()) {
            System.out.println("node-thread=" + node.getThread() + ";owner-thread=" +getExclusiveOwnerThread() );
            System.out.println("node=" + node.toString());
            System.out.println("state=" + state);
            throw new RuntimeException("unlock err");
        }
        //在node.setLocked(false) 之前设置 state
        --state;
        //完全释放锁，locked改为false，让其后继感知前驱锁释放并停止自旋
        if (state == 0) {
            setExclusiveOwnerThread(null);
            node.setLocked(false);
            threadNode.remove();
            System.out.println("clh un lock ok, thread=" + node.getThread().getId());
        }
    }
}
