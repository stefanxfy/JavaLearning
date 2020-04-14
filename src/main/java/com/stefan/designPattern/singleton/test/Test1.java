package com.stefan.designPattern.singleton.test;

import com.stefan.designPattern.singleton.ThreadLocalIdGenerator;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println(HungryIdGenerator.getInstance().getId());
//        System.out.println(Lazy3IdGenerator.INSTANCE.getId());
//        Logger.INSTATCE.log("msg");
//        Logger.INSTATCE.log("123");

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("sub1: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());
                System.out.println("sub2: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());

            }
        };
        thread.start();
        thread.join();
        System.out.println("main1: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());
        System.out.println("main2: " + ThreadLocalIdGenerator.getIntance().toString() + "-->" + ThreadLocalIdGenerator.getIntance().getId());
    }
}
