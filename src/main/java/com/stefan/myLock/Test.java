package com.stefan.myLock;

public class Test {
    public static int s = 1000;
    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        TicketLock ticketLock = new TicketLock();
        CLHLock clhLock = new CLHLock();
        MCSLock mcsLock = new MCSLock();
        long c = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mcsLock.lock();
                    s--;
                    mcsLock.unlock();

                }
            });
            thread.start();
        }
        long e = System.currentTimeMillis();
        System.out.println("ms=" + (e - c));
        Thread.sleep(2000);
        System.out.println("s=" + s);

    }
}
