package com.stefan.DailyTest;

import java.util.concurrent.atomic.AtomicReference;

public class Test22 {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
//        Runtime.getRuntime().exec();
        Integer i = threadLocal.get();
        System.out.println(i);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    System.out.println("-----------");
                }
            }
        });
        thread.start();
        thread.interrupt();
    }
}
