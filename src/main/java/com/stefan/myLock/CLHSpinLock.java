package com.stefan.myLock;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 公平锁，FIFO 能确保无饥饿性，提供先来先服务的公平性。
 * CLH的特点是：公平性，获取锁的线程先入队列，入到队列尾部后不断自旋检查前驱节点的状态，前驱为空 or前驱释放锁则该节点获取锁。
 *
 * 节点获取锁的条件：前驱为空or前驱释放锁。
 * CLH在SMP系统结构下该法是非常有效的。
 * 但在NUMA系统结构下，每个线程有自己的内存，如果前趋结点的内存位置比较远，自旋判断前趋结点的locked域，
 * 性能将大打折扣，一种解决NUMA系统结构的思路是MCS队列锁。
 */
public class CLHSpinLock {
    class Node {
        volatile Node prev;
        /**
         * true表示正在持有锁，或者需要锁
         * false表示释放锁
         */
        volatile boolean locked = true;
        volatile Thread thread;
        Node(Thread thread) {
            this.thread = thread;
        }

        boolean isPrevLocked() {
            return  prev != null && prev.locked;
        }
        String getPrevName() {
            return prev == null ? "null" : prev.thread.getName();
        }
    }

    private volatile AtomicReference<Node> tail = new AtomicReference<Node>();
    //线程和node key-value
    private ThreadLocal<Node> threadNode = new ThreadLocal<Node>();
    //记录持有锁的当前线程
    private volatile Thread exclusiveOwnerThread;
    //记录重入
    protected volatile int state = 0;
    //因为exclusiveOwnerThread和state只是作为记录，线程获取锁后才会设置这两个值，具有有瞬时性，所以不能作为锁是否空闲的判断标志

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
        if (node != null && getExclusiveOwnerThread() != null && node.thread == getExclusiveOwnerThread()) {
            /**
             * 一般情况node != null，说明有同一个线程已经调用了lock()
             * 判断持有锁的线程是node.thread，重入
             */
            state++;
            System.out.println(String.format("re lock thread=%s;state=%d;", node.thread.getName(), state));
            return;
        }
        node = enq();
        while (node.isPrevLocked()) {

        }
        //前驱未持有锁，说明可以获取锁，即获取锁成功, prev设置为null，断开与链表的连接，相当于前驱出队列
        System.out.println(String.format("clh get lock ok, thread=%s;prev=%s;", node.thread.getName(), node.getPrevName()));
        setExclusiveOwnerThread(node.thread);
        state++;
        node.prev = null;
    }

    public void unlock() {
        Node node = threadNode.get();
        if (node.thread != getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException();
        }
        //在node.setLocked(false) 之前设置 state
        --state;
        //完全释放锁，locked改为false，让其后继感知前驱锁释放并停止自旋
        if (state == 0) {
            System.out.println(String.format("clh un lock ok, thread=%s;", node.thread.getName()));
            setExclusiveOwnerThread(null);
            node.locked = false;
            threadNode.remove();
            node = null; //help gc
        }
    }
}
