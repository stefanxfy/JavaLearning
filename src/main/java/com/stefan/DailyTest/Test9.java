package com.stefan.DailyTest;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1、核心参数解读corePoolSize、maximumPoolSize、keepAliveTime  1
 * 2、workQueue 阻塞队列解读
 * 3、ThreadFactory解读 1
 * 4、RejectedExecutionHandler解读  1
 * 5、execute解读  1
 * 6、Worker解读  1
 * 7、submit解读与execute对比
 * 8、shutdown\shutdownNow解读与对比  1
 * 9、awaitTermination解读  1
 */
public class Test9 {
    public static void main(String[] args) {
        LinkedBlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5,
                30, TimeUnit.SECONDS, workQueue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        });
//        poolExecutor.execute();
//        poolExecutor.submit();
//        poolExecutor.shutdown();
//        poolExecutor.shutdownNow();
//        poolExecutor.awaitTermination()


    }
}
