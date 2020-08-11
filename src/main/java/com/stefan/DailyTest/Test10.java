package com.stefan.DailyTest;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 1. FutureTask 实现于Runnable、Future
 * 2. ScheduledFutureTask继承自FutureTask，多实现了一个Delayed接口
 * 3. 延时阻塞队列
 */
public class Test10 {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
//        executor.execute();
//        executor.scheduleAtFixedRate();
//        executor.scheduleWithFixedDelay();
    }
}
