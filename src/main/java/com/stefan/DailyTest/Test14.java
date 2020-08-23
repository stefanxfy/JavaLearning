package com.stefan.DailyTest;

import java.util.concurrent.*;

public class Test14 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LinkedBlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5,
                30, TimeUnit.SECONDS, workQueue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        });
        Future<?> future = poolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("...");
            }
        });
        future.get();
    }
}
