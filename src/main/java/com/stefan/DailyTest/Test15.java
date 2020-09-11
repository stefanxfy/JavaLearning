package com.stefan.DailyTest;

public class Test15 {
    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    /** Returns the number of shared holds represented in count  */
    //共享锁（读锁）重入的次数
    static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
    /** Returns the number of exclusive holds represented in count  */
    //独占锁（写锁）重入的次数
    static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }
    public static int test(int i) {
        for (;;) {
            if (i > 1) {
                return i;
            }
        }
    }
    public static void main(String[] args) {
//        int i = 1;
//        int j = 2;
//        System.out.println(2 | 1);
//
//        System.out.println(new Son());
//        System.out.println(test(1));
        int c = 0;
        c = c + SHARED_UNIT;
        c = c + SHARED_UNIT;
        System.out.println(c);
        System.out.println(MAX_COUNT);
        System.out.println(sharedCount(c));

    }
}
