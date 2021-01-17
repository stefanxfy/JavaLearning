package com.stefan.DailyTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test14 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5,
//                30, TimeUnit.SECONDS, workQueue);
//        Future<Integer> future = poolExecutor.submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                int i = 100;
//                int j = 100;
//                int sum = i + j;
//                Thread.sleep(2000);
//                return sum;
//            }
//        });
//        future.cancel(false);
//        long s = System.currentTimeMillis();
//        Integer result = future.get();
//        long e = System.currentTimeMillis();
//        System.out.println("result=" + result + ",ms=" + (e-s));

        List<FutureTask> taskList = new ArrayList<FutureTask>();
        for (int i = 0; i < 3; i++) {
            int j = i;
            FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return j + 10;
                }
            });
            Thread thread = new Thread(futureTask);
            thread.start();
            taskList.add(futureTask);
        }
        //批量获取结果
        for (FutureTask futureTask : taskList) {
            System.out.println(futureTask.get());
        }
    }
}
