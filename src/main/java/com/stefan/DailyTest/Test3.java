package com.stefan.DailyTest;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("run.....");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("-------------");
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("被意外停止了。。。。");
            }
        });
        Thread.sleep(3000);
        System.out.println("3s 过了。。。。");
    }
}
