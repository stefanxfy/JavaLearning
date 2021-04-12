package com.stefan.DailyTest;

public class InheritableThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        InheritableThreadLocal<Stu> itl = new MyInheritableThreadLocal<Stu>();
        itl.set(new Stu());
        System.out.println("主线程：" + itl.get().toString());
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程1：" + itl.get().toString());
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程2：" + itl.get().toString());
            }
        });
        thread2.start();
    }

    static class Stu {
        private String name = "xxx";
    }
}
