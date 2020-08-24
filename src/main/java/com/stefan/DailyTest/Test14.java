package com.stefan.DailyTest;

import java.util.concurrent.*;

public class Test14 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5,
                30, TimeUnit.SECONDS, workQueue);
        poolExecutor.execute();
        Future<Integer> future = poolExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i = 100;
                int j = 100;
                int sum = i + j;
                Thread.sleep(2000);
                return sum;
            }
        });
        long s = System.currentTimeMillis();
        Integer result = future.get();
        long e = System.currentTimeMillis();
        System.out.println("result=" + result + ",ms=" + (e-s));

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.execute();
    }
}
