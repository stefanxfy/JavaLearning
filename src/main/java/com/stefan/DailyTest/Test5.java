package com.stefan.DailyTest;

import java.util.concurrent.atomic.AtomicInteger;

public class Test5 {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger ai = new AtomicInteger(0);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ai.incrementAndGet();
                ai.decrementAndGet();
            }
        });
        thread.start();
        thread.join();
        System.out.println(ai.incrementAndGet());
    }
}
