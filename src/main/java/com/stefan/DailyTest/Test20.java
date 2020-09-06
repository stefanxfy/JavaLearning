package com.stefan.DailyTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Test20 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);
        semaphore.acquire();
        semaphore.acquireUninterruptibly();
        semaphore.release();
        semaphore.tryAcquire();
        semaphore.acquireUninterruptibly();
        semaphore.availablePermits();
        semaphore.drainPermits();
        System.out.println(semaphore.availablePermits());

        CountDownLatch countDownLatch = new CountDownLatch(10);
        countDownLatch.await();//主线程调用await()被阻塞
        countDownLatch.countDown(); //10个worker线程执行完任务countDown()，当state减到0，主线程被唤醒
    }
}
