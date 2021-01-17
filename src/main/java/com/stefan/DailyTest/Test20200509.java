package com.stefan.DailyTest;

public class Test20200509 {
    public static void test() {
        throw new RuntimeException("no matching option");
    }
    public static void main(String[] args) throws InterruptedException {
//        test();
        System.out.println("start....");
//        Thread.sleep(100000000000L);
        System.out.println("end....");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("被意外停止了。。。。");
            }
        });
//        test();
        Thread.sleep(10000);


    }
}
