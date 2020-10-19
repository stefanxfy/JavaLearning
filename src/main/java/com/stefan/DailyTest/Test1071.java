package com.stefan.DailyTest;

import java.util.concurrent.CountDownLatch;

public class Test1071 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " invoke countDownLatch.await()");
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + ",被唤醒。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("countDown..");
                    countDownLatch.countDown();
                }
            }).start();
        }
    }
}
