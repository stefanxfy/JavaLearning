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
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }

    public static void main(String[] args) {
        System.out.println("COUNT_BITS=" + COUNT_BITS);
        System.out.println("CAPACITY=" + CAPACITY);

        System.out.println("RUNNING=" + RUNNING);
        System.out.println("SHUTDOWN=" + SHUTDOWN);
        System.out.println("STOP=" + STOP);
        System.out.println("TIDYING=" + TIDYING);
        System.out.println("TERMINATED=" + TERMINATED);

        System.out.println("runStateOf=" + runStateOf(STOP));
        System.out.println("workerCountOf=" + workerCountOf(1213));
        System.out.println("ctlOf=" + ctlOf(RUNNING, 0));
//        LinkedBlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5,
//                30, TimeUnit.SECONDS, workQueue);
//        poolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("线程池运行任务1");
//            }
//        });
//        poolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("线程池运行任务2");
//            }
//        });
//        poolExecutor.shutdown();


    }
}
