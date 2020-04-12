package com.stefan.designPattern.singleton;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println(HungryIdGenerator.getInstance().getId());
//        System.out.println(Lazy3IdGenerator.INSTANCE.getId());
//        Logger.INSTATCE.log("msg");
//        Logger.INSTATCE.log("123");

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("sub1: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());
                System.out.println("sub2: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());

            }
        };
        thread.start();
        thread.join();
        System.out.println("main1: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());
        System.out.println("main2: " + ThreadIdGenerator2.getIntance().toString() + "-->" + ThreadIdGenerator2.getIntance().getId());
    }
}
