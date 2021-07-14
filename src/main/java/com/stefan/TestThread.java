package com.stefan;

public class TestThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("用户线程正在运行...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
