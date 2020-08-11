package com.stefan.DailyTest;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test7 {
    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        System.out.println(s);
        System.out.println(TimeUnit.SECONDS.toSeconds(s));
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10 , 100, TimeUnit.HOURS, new LinkedBlockingDeque<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        threadPoolExecutor.shutdown();
        //threadPoolExecutor.shutdownNow();
        try {
            boolean loop = true;
            do {
                loop = !threadPoolExecutor.awaitTermination(2, TimeUnit.SECONDS);

            } while (loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
