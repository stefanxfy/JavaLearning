package com.stefan.DailyTest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Tes7 {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock().lock();
        readWriteLock.readLock().unlock();
    }
}
