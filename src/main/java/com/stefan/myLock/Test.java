package com.stefan.myLock;

public class Test {
    public static int s = 100;
    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        TicketLock ticketLock = new TicketLock();
        CLHLock clhLock = new CLHLock();
        long c = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    clhLock.lock();
                    s--;
                    clhLock.unlock();
                }
            });
            thread.start();
        }
        long e = System.currentTimeMillis();
        System.out.println("ms=" + (e - c));
        Thread.sleep(2000);
        System.out.println(s);

    }
}
