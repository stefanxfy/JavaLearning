package com.stefan.DailyTest;

import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<User> threadLocal = new ThreadLocal<>();

        try {
            threadLocal.set(new User());
            threadLocal.get();
        } finally {
//            threadLocal.remove();
        }

        TimeUnit.MINUTES.sleep(5);
    }

    static class User {
        private byte[] datas = new byte[1024*1024*100];
    }

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();
}
