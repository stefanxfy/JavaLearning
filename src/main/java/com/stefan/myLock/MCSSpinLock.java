package com.stefan.myLock;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * 公平锁，FIFO
 * MCS的特点是：公平性，获取锁的线程先入队列，入到队列尾部后不断自旋检查自己节点的状态，前驱为空 or自己节点状态可以获取锁了则获取锁。
 * 节点获取锁的条件：前驱为空or自己节点状态为可获取锁
 * 参考：
 * https://www.ibm.com/developerworks/cn/linux/l-cn-mcsspinlock/
 *
 * 为了解决自旋锁可扩展性问题，学术界提出了许多改进版本，
 * 其核心思想是：每个锁的申请者（处理器）只在一个本地变量上自旋。
 * MCS Spinlock 和 CLH Spinlock 都是基于链表结构的自旋锁（还有一些基于数组的自旋锁）。MCS Spinlock的设计目标如下：
 *
 * 保证自旋锁申请者以先进先出的顺序获取锁（FIFO Ordering）。
 * 只在本地可访问的标志变量上自旋。
 * 在处理器个数较少的系统中或锁竞争并不激烈的情况下，保持较高性能。
 * 自旋锁的空间复杂度（即锁数据结构和锁操作所需的空间开销）为常数。
 * 在没有处理器缓存一致性协议保证的系统中也能很好地工作。
 */
public class MCSSpinLock {
    class Node {
        //prev 可有可无
        volatile Node prev;
        volatile Node next;
        //false代表未持有锁，true代表可持有锁
        volatile boolean locked = false;
        volatile boolean isCancel = false;
        volatile Thread thread;
        Node(Thread thread) {
            this.thread = thread;
        }

        public boolean shouldLocked() {
            return prev == null || locked;
        }

        public String getNextName() {
            return next == null ? "null" : next.thread.getName();
        }
    }
    private volatile AtomicReference<Node> tail = new AtomicReference<Node>();
    //线程和node key-value
    private ThreadLocal<Node> threadOwnerNode = new ThreadLocal<Node>();
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
        threadOwnerNode.set(node);
        for (;;) {
            Node t = tail.get();
            if (tail.compareAndSet(t, node)) {
                if (t != null) {
                    t.next = node;
                    node.prev = t;
                }
                return node;
            }
        }
    }

    /**
     *
     * @return 返回前驱
     */
    Node enq1() {
        Node node = new Node(Thread.currentThread());
        threadOwnerNode.set(node);
        Node prev = tail.getAndSet(node);
        if (prev != null) {
            prev.next = node;
        }
        return prev;
    }

    public void lock() {
        Node node = threadOwnerNode.get();
        if (node != null && getExclusiveOwnerThread() != null && node.thread == getExclusiveOwnerThread()) {
            /**
             * 一般情况node != null，说明有同一个线程已经调用了lock()
             * 持有锁的线程是node.thread，重入
             */
            state++;
            System.out.println("re lock thread=" + node.thread.getId() + "state=" + state);

            return;
        }
        node = enq();
        while (!node.shouldLocked()) {}
        //判断node是否应该获取锁，若prev == null or node.locked=true，代表应该获取锁。则结束自旋
        if (!node.locked) {
            //当前驱为空时的情况，不过不改也问题不大
            node.locked = true;
        }
        state++;
        setExclusiveOwnerThread(node.thread);
        node.prev = null;
        System.out.println(String.format("mcs get lock ok, thread=%s;locked=%b;node==tail=%b;next=%s;", node.thread.getName(), node.locked, node == tail.get(), node.getNextName()));
    }

    public void lock1() {
        Node node = threadOwnerNode.get();
        if (node != null && getExclusiveOwnerThread() != null && node.thread == getExclusiveOwnerThread()) {
            /**
             * 一般情况node != null，说明有同一个线程已经调用了lock()
             * 持有锁的线程是node.thread，重入
             */
            state++;
            System.out.println("re lock thread=" + node.thread.getId() + "state=" + state);

            return;
        }
        Node prev = enq1();
        node = threadOwnerNode.get();
        while (prev != null && !node.locked) {}
        //判断node是否应该获取锁，若prev == null or node.locked=true，代表应该获取锁。则结束自旋
        if (!node.locked) {
            //当前驱为空时的情况，不过不改也问题不大
            node.locked = true;
        }
        state++;
        setExclusiveOwnerThread(node.thread);
        System.out.println(String.format("mcs get lock ok, thread=%s;locked=%b;node==tail=%b;next=%s;", node.thread.getName(), node.locked, node == tail.get(), node.getNextName()));
    }

    public void unlock() {
        Node node = threadOwnerNode.get();
        if (node.thread != getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException();
        }
        //在node.setLocked(false) 之前设置 state
        state--;
        //完全释放锁，将前驱 locked改为false，让其后继感知锁空闲并停止自旋
        if (state != 0) {
            return;
        }
        //后继为空，则清空队列，将tail  cas为null，
        //如果此时刚好有节点入队列则退出循环，继续主动通知后继
        while (node.next == null) {
            if (tail.compareAndSet(node, null)) {
                //设置 tail为 null,threadOwnerNode remove
                threadOwnerNode.remove();
                System.out.println(String.format("mcs un lock ok, thread=%s;clear queue", node.thread.getName()));
                return;
            }
        }
        //threadOwnerNode 后继不为空 设置后继的locked=true，主动通知后继获取锁
        System.out.println(String.format("mcs un lock ok, thread=%s;next-thread=%s;", node.thread.getName(), node.getNextName()));
        //在node.next.locked前，设置setExclusiveOwnerThread 为null
        setExclusiveOwnerThread(null);
        threadOwnerNode.remove();
        node.next.locked = true;
        node.next = null;
        node = null; //help gc
    }

    /**
     * 双向队列才可以取消节点，即当前节点知道自己的前驱和后继
     */
    private void cancelAcquire() {
        Node node = threadOwnerNode.get();
        if (node == null) {
            throw new NullPointerException();
        }
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.next.prev = prev;
    }
}
