package com.stefan.DailyTest;

import java.util.concurrent.atomic.AtomicReference;

public class Test22 {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
//        Runtime.getRuntime().exec();
        Integer i = threadLocal.get();
        System.out.println(i);
    }
}
