package com.stefan.myLock;

public class Test {
    public static int s = 1000;
    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        TicketSpinLock ticketSpinLock = new TicketSpinLock();
        CLHSpinLock clhSpinLock = new CLHSpinLock();
        MCSSpinLock mcsSpinLock = new MCSSpinLock();
        long c = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mcsSpinLock.lock();
                    s--;
                    mcsSpinLock.unlock();
                }
            });
            thread.start();

        }
        long e = System.currentTimeMillis();
        Thread.sleep(2000);
        System.out.println("s=" + s);

    }
}
