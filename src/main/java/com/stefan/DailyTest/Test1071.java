package com.stefan.DailyTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test1071 {
    public static void main(String[] args) throws InterruptedException {
/*        CountDownLatch countDownLatch = new CountDownLatch(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " invoke countDownLatch.await()");
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + ",被唤醒。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("countDown..");
                    countDownLatch.countDown();
                }
            }).start();
        }*/
/*        String serviceName = "GfwSvr";
        int svrIndex = serviceName.lastIndexOf("Svr");
        if (svrIndex > 0) {
            String prefix = serviceName.substring(0, svrIndex);
            System.out.println(prefix);
            serviceName = prefix.concat("Cli");
        }
        System.out.println(serviceName);*/
        List<String> list = new ArrayList<>();
        list.add("xxx");
        list.add("222");
        String[] arr = list.toArray(new String[]{});
        for (String s : arr) {
            System.out.println(s);
        }
        System.out.println(arr);

    }
}
