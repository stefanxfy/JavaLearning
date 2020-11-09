package com.stefan.DailyTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.concurrent.*;

public class Test1108 {
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr.getHostName());
        System.out.println(addr.getHostAddress());
        System.out.println(addr.getAddress());
        System.out.println(addr.getCanonicalHostName());
//        FutureTask<Integer> task = new FutureTask<Integer>(new Callable() {
//            @Override
//            public Integer call() throws Exception {
//                int i = 10;
//                int j = 11;
//                Thread.sleep(10000);
//                return i + j;
//            }
//        });
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//
//        thread.start();
//        System.out.println(thread.isDaemon());
//        System.out.println("----");
//        Integer rt = task.get();
//        System.out.println(rt);
        LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10, 30, TimeUnit.SECONDS, blockingQueue);
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            Future<Integer> future = threadPoolExecutor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(2000);
                    return 10 + finalI;
                }
            });
            System.out.println(future.get());
        }
        System.out.println("-----------");
        threadPoolExecutor.shutdown();
    }
}
