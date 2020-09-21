package com.stefan.myLock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SpinLock implements Lock {
    protected volatile int state = 0;
    private volatile Thread exclusiveOwnerThread;
    public void lock() {
        for(;;) {
            //直到获取锁成功，才结束循环
            if (tryAcquire(1)) {
                return;
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        for(;;) {
            if (Thread.interrupted()) {
                //有被中断  抛异常
                throw new InterruptedException();
            }
            if (tryAcquire(1)) {
                return;
            }
        }
    }

    @Override
    public boolean tryLock() {
        return tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long nanosTimeout = unit.toNanos(time);
        if (nanosTimeout <= 0L) {
           return false;
        }
        final long deadline = System.nanoTime() + nanosTimeout;
        for(;;) {
            if (Thread.interrupted()) {
                //有被中断  抛异常
                throw new InterruptedException();
            }
            if (tryAcquire(1)) {
                return true;
            }
            nanosTimeout = deadline - System.nanoTime();
            if (nanosTimeout <= 0L) {
                return false;
            }
        }
    }

    @Override
    public void unlock() {
        tryRelease(1);
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    public int getState() {
        return state;
    }

    /**
     * 获取持有锁的当前线程
     * @return
     */
    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    /**
     * 获取当前线程重入次数
     * @return
     */
    public int getHoldCount() {
        return isHeldExclusively() ? getState() : 0;
    }

    /**
     * 释放锁
     * @param releases
     * @return
     */
    protected boolean tryRelease(int releases) {
        int c = getState() - releases;
        if (Thread.currentThread() != getExclusiveOwnerThread())
            //不是当前线程，不能unLock 抛异常
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c <= 0) {
            //每次减一，c = 0,证明没有线程持有锁了，可以释放了
            free = true;
            c = 0;
            setExclusiveOwnerThread(null);
        }
        //排它锁，只有当前线程才会走到这，是线程安全的  修改state
        setState(c);
        return free;
    }
    /**
     * 获取锁
     * @param acquires
     * @return
     */
    protected boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            //若此时锁空着，则再次尝试抢锁
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
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
        return false;
    }
    /**
     * 判断当前线程是否持有锁
     * @return
     */
    protected final boolean isHeldExclusively() {
        return getExclusiveOwnerThread() == Thread.currentThread();
    }

    protected void setState(int state) {
        this.state = state;
    }

    protected void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }
    protected static final Unsafe getUnsafe() {
        try {
            //不可以直接使用Unsafe，需要通过反射，当然也可以直接使用atomic类
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            if (unsafe != null) {
                return unsafe;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("get unsafe is null");
    }
    private static final Unsafe unsafe = getUnsafe();
    private static final long stateOffset;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (SpinLock.class.getDeclaredField("state"));
        } catch (Exception ex) { throw new Error(ex); }
    }

}
