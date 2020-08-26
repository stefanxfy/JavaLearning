package com.stefan.DailyTest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test13 {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock().lock();
        //java.util.concurrent.locks.ReentrantReadWriteLock.Sync.tryAcquireShared
        readWriteLock.readLock().unlock();
        readWriteLock.writeLock().lock();
        readWriteLock.writeLock().unlock();
    }
}
