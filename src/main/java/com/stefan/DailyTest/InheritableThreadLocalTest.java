package com.stefan.DailyTest;

import java.util.concurrent.TimeUnit;

public class InheritableThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        InheritableThreadLocal<String> itl = new InheritableThreadLocal<>();

        itl.set("徐同学呀");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(itl.get());
            }
        });
        thread.start();
        TimeUnit.MINUTES.sleep(10);
    }
}
