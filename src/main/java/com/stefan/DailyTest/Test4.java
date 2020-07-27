package com.stefan.DailyTest;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS ABA问题，对于递增、递减的数字型数据即使出现ABA问题也不影响，比如库存的加减。
 * ABA的解决方案就是 加上版本号的判断。原先的cas只是比较值，取到的值和旧值是否相等，相等就失没有修改过。
 *
 */
public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        Integer initialRef = 100;
        int initialStamp = 0;
        AtomicStampedReference<Integer> intAsr = new AtomicStampedReference<Integer>(initialRef, initialStamp);
//

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                intAsr.compareAndSet(intAsr.getReference(), intAsr.getReference() + 1, intAsr.getStamp(), intAsr.getStamp() + 1);
                intAsr.compareAndSet(intAsr.getReference(), intAsr.getReference() - 1, intAsr.getStamp(), intAsr.getStamp() + 1);
            }
        });
        t.start();
        t.join();
        boolean flag = intAsr.compareAndSet(intAsr.getReference(), intAsr.getReference() + 1, intAsr.getStamp(), intAsr.getStamp() + 1);
        System.out.println(flag);
        System.out.println(intAsr.getReference());
        System.out.println(intAsr.getStamp());
    }
}
