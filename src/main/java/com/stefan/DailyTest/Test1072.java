package com.stefan.DailyTest;

import java.util.concurrent.Semaphore;

public class Test1072 {
    private static Semaphore semaphore = new Semaphore(5);
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MyThread(i));
            thread.start();
        }
    }

    static class MyThread implements Runnable{
        private int i;
        public MyThread(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ", semaphore.acquire()获取资源--" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
//                System.out.println(Thread.currentThread().getName() + ",semaphore.release()释放资源--" + i);
//                semaphore.release();
            }
        }
    }
}
