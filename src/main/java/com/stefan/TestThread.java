package com.stefan;

public class TestThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("hhhhhhhh");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
