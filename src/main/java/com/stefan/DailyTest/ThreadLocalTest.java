package com.stefan.DailyTest;

import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("徐同学呀");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("value=" + tl.get());
            }
        });
        t.start();

    }
}
