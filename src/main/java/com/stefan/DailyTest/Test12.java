package com.stefan.DailyTest;

import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantLock实现接口Lock
 * 有一个内部类Sync，同步器提供所有的实现机制
 * Sync继承了AbstractQueuedSynchronizer（AQS）
 * Sync主要函数nonfairTryAcquire()、tryRelease()、newCondition()
 * 公平锁FairSync、非公平锁NonfairSync，都继承了Sync，并分别实现了lock和tryAcquire
 *
 * AbstractQueuedSynchronizer extend  AbstractOwnableSynchronizer
 * Node 双向队列
 * ConditionObject 双向队列
 *
 * AbstractOwnableSynchronizer   setExclusiveOwnerThread、getExclusiveOwnerThread
 */
public class Test12 {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        lock.tryLock();
        lock.lockInterruptibly();
        Condition condition = lock.newCondition();
        condition.await();
        condition.signal();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.writeLock().newCondition();
        readWriteLock.readLock().lock();
        readWriteLock.readLock().unlock();
        readWriteLock.writeLock().lock();
        readWriteLock.writeLock().unlock();
        readWriteLock.readLock().newCondition();
    }
}
