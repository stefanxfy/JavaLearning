package com.stefan.DailyTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1. FutureTask 实现于Runnable、Future
 * 2. ScheduledFutureTask继承自FutureTask，多实现了一个Delayed接口
 * 3. 延时阻塞队列
 */
public class Test10 {
    public static void main(String[] args) {
//        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
//        executor.execute();
//        executor.schedule()
//        executor.scheduleAtFixedRate();
//        executor.scheduleWithFixedDelay();
//        Map<String, Integer> map = new HashMap<>();
//        int i = map.get("1");
//        System.out.println(i);

        String s = "sds;dvfvf;sfcx;";
        System.out.println(s.substring(0,s.length() - 1));
//        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//        readWriteLock.readLock().lock();
//        readWriteLock.writeLock().lock();
        Boolean b = null;
        boolean bb = b;
        System.out.println(bb);

    }
}
