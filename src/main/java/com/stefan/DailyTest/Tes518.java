package com.stefan.DailyTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Tes518 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 业务destory省略
        // 此时需要立刻停止线程池
        threadPoolExecutor.shutdownNow();
        try {
            threadPoolExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // Ignore
        }
        if (threadPoolExecutor.isTerminating()) {
            System.out.println("warn.executorShutdown, alarm to user");
        }
        if (threadPoolExecutor.isTerminated()) {
            System.out.println("destory...");
        }
    }
}
