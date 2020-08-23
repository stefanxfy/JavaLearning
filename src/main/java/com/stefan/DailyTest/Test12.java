package com.stefan.DailyTest;

import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
    }
}
