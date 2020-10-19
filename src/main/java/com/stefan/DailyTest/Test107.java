package com.stefan.DailyTest;

public class Test107 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        System.out.println("run...");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        throw new IllegalMonitorStateException();
                    } finally {
                        System.out.println("finally...");
                    }
                }
            }
        });
        thread.start();
    }
}
