package com.stefan.myLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参考：
 * https://www.ibm.com/developerworks/cn/linux/l-cn-spinlock/
 *
 * 传统自旋锁的“不公平”问题在锁竞争激烈的服务器系统中尤为严重，
 * 因此 Linux 内核开发者 Nick Piggin 在 Linux 内核 2.6.25 版本中引入了排队自旋锁：通过保存执行线程申请锁的顺序信息来解决“不公平”问题。
 *
 * 排队自旋锁TicketLock仍然使用原有SpinLock的数据结构，为了保存顺序信息，加入了两个新变量。
 * 分别是锁需要服务的序号(serviceNum)和未来锁申请者的票据序号(ticketNumber)
 * 当serviceNum=ticketNum时，代表锁空闲，线程可以获取锁。
 *
 * 但是在大规模多处理器系统和 NUMA系统中，
 * 排队自旋锁（包括传统自旋锁）存在一个比较严重的性能问题：由于执行线程均在同一个共享变量上自旋，
 * 这将导致所有参与排队自旋锁操作的处理器的缓存变得无效。
 * 如果排队自旋锁竞争比较激烈的话，频繁的缓存同步操作会导致繁重的系统总线和内存的流量，从而大大降低了系统整体的性能。
 */
public class TicketSpinLock {
    //服务序号，不需要cas，因为释放锁的只有一个线程，serviceNum++的环境是天生安全的
    private volatile int serviceNum = 0;
    //排队序号，cas
    private AtomicInteger ticketNum = new AtomicInteger(0);
    //记录当前线程的排队号，主要的作用是为了实现可重入，防止多次取号
    private ThreadLocal<Integer> threadOwnerTicketNum = new ThreadLocal<Integer>();
    //state不作为锁状态标志，只代表锁重入的次数
    protected volatile int state = 0;
    private volatile Thread exclusiveOwnerThread;
    public void lock() {
        final Thread current = Thread.currentThread();
        Integer myTicketNum = threadOwnerTicketNum.get();
        if (myTicketNum == null) {
            myTicketNum = ticketNum.getAndIncrement();
            threadOwnerTicketNum.set(myTicketNum);
            while (serviceNum != myTicketNum) {}
            //若拿的排队号刚好等于服务序号，说明可以获取锁，即获取锁成功
            setExclusiveOwnerThread(current);
            state ++ ;
            System.out.println(String.format("ticket lock ok, thread=%s;state=%d;serviceNum=%d;next-ticketNum=%d;", current.getName(), getState(), serviceNum, ticketNum.get()));
            return;
        }
        //若已经取号，判断当前持锁线程是当前线程(重入性)
        if (current == getExclusiveOwnerThread()) {
            //重入
            state++;
            System.out.println(String.format("ticket re lock ok, thread=%s;state=%d;serviceNum=%d;next-ticketNum=%d;", current.getName(), getState(), serviceNum, ticketNum.get()));
            return;
        }
    }
    public void unlock() {
        if (Thread.currentThread() != getExclusiveOwnerThread())
            //不是当前线程，不能unLock 抛异常
            throw new IllegalMonitorStateException();
        state--;
        if (state == 0) {
            //完全释放锁，owner+1
            //服务序号是线性安全的，无需cas
            threadOwnerTicketNum.remove();
            setExclusiveOwnerThread(null);
            serviceNum ++;
            System.out.println(String.format("ticket un lock ok, thread=%s;next-serviceNum=%d;ticketNum=%d;", Thread.currentThread().getName(), serviceNum, ticketNum.get()));
        }
    }

    public int getState() {
        return state;
    }

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
}
