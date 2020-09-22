package com.stefan.myLock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * SMP NUMP https://www.cnblogs.com/yubo/archive/2010/04/23/1718810.html
 *
 * 自旋锁是为实现保护共享资源而提出一种锁机制。自旋锁与java中的synchronized和Lock不同，不会引起调用线程阻塞睡眠，如果有线程持有自旋锁了，
 * 调用线程就一直循环检测锁的状态，直到其他线程释放锁，调用线程获取锁而停止自旋。
 *
 * SpinLock 自旋锁，是非公平锁，多个线程竞争检测一个变量
 * 可重入，state可作为锁是否空闲的标志，state=0 锁空闲，state>0 代表某线程持有，数值为重入次数。
 *
 * 自旋锁的优点：
 * 1.自旋锁不会使线程状态进行切换，一直处于用户态，即不会频繁产生上下文切换，执行速度快，性能高。
 *
 * SpinLock缺点：
 * 1.如果某个线程持有锁的时间过长，就会导致其它等待获取锁的线程进入循环等待，消耗CPU。使用不当会造成CPU占用率极高。
 * 2.不公平性，无法满足等待时间最长的线程优先获取锁，造成 “线程饥饿”。
 * 3.由于每个申请自旋锁的处理器均在一个全局变量上自旋检测，系统总线将因为处理器间的缓存同步而导致繁重的流量，从而降低了系统整体的性能。
 *
 * 由于传统自旋锁无序竞争的本质特点，内核执行线程无法保证何时可以取到锁，某些执行线程可能需要等待很长时间，导致“不公平”问题的产生。这有两方面的原因：
 * 1.随着处理器个数的不断增加，自旋锁的竞争也在加剧，自然导致更长的等待时间。
 * 2.释放自旋锁时的重置操作将无效化所有其它正在自旋等待的处理器的缓存，那么在处理器拓扑结构中临近自旋锁拥有者的处理器可能会更快地刷新缓存，因而增大获得自旋锁的机率。
 *
 * 自旋锁适用场景：
 * 自旋锁适用于被锁代码块执行时间很短，即加锁时间很短的场景。
 *
 */
public class SpinLock implements Lock {
    protected volatile int state = 0;
    private volatile Thread exclusiveOwnerThread;
    @Override
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

    /**
     * 返回获取锁的结果，不会自旋
     * @return
     */
    @Override
    public boolean tryLock() {
        return tryAcquire(1);
    }

    /**
     * 返回获取自旋锁的结果，会自旋一段时间，超时后停止自旋
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
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
        Thread current = Thread.currentThread();
        if (current != getExclusiveOwnerThread())
            //不是当前线程，不能unLock 抛异常
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c <= 0) {
            //每次减一，c = 0,证明没有线程持有锁了，可以释放了
            free = true;
            c = 0;
            setExclusiveOwnerThread(null);
            System.out.println(String.format("spin un lock ok, thread=%s;", current.getName()));
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
                System.out.println(String.format("spin lock ok, thread=%s;", current.getName()));
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
            System.out.println(String.format("spin re lock ok, thread=%s;state=%d;", current.getName(), getState()));
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
