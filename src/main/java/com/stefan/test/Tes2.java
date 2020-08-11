package com.stefan.test;

public class Tes2 {
    private static int i = 10;
    private static boolean flag = false;
    public static synchronized void test1(String name) {
        System.out.println("test1" + name);
    }
    public static synchronized void test2() {
        test1("test2");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test2");
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                test2();
            }
        });
        t.setDaemon(true);
        t.start();
        t.join();
        System.out.println("----------------");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("等待锁");
                test1("t2");
            }
        });
        t2.setDaemon(true);
        t2.start();
        t2.interrupt();
        t.wait();
        System.out.println(t2.isInterrupted());
        System.out.println(Thread.interrupted());

    }
}
