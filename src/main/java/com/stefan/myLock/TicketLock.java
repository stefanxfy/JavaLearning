package com.stefan.myLock;

import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock extends SpinLock {
    private volatile int owner = 0;
    private AtomicInteger next = new AtomicInteger(0);

    protected boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            //c==0，,说明此时锁空着，则拿号排队获取锁
            int myTicketNum = next.getAndIncrement();
            if (owner == myTicketNum) {
                //若拿的排队号刚好等于owner，说明可以获取锁，即获取锁成功
                setExclusiveOwnerThread(current);
                //重入+1
                setState(acquires);
                return true;
            }
        }
        //若当前持锁线程是当前线程(重入性)
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                throw new Error("Maximum lock count exceeded");
            //重入
            setState(nextc);
            return true;
        }
//        System.out.println("tryAcquire false, thread=" + current.getId());
        return false;
    }

    protected boolean tryRelease(int releases) {
        int c = getState() - releases;
        if (Thread.currentThread() != getExclusiveOwnerThread())
            //不是当前线程，不能unLock 抛异常
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c <= 0) {
            //完全释放锁，owner+1
            owner ++;
            free = true;
            c = 0;
            setExclusiveOwnerThread(null);
        }
        //排它锁，只有当前线程才会走到这，是线程安全的  修改state
        setState(c);
        return free;
    }
}
