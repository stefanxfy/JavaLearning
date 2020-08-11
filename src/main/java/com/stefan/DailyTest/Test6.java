package com.stefan.DailyTest;

public class Test6 {
    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;
    static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }
    static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
    public static void main(String[] args) {
        System.out.println("exclusiveCount.." + exclusiveCount(65539));
        System.out.println("sharedCount.." + sharedCount(12));
        System.out.println(SHARED_UNIT);
        System.out.println(MAX_COUNT);
        System.out.println(EXCLUSIVE_MASK);
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(65536 << SHARED_SHIFT));
        System.out.println(Integer.toBinaryString(6553 >>> SHARED_SHIFT));
        System.out.println(Integer.toBinaryString(65539 + SHARED_UNIT));
        System.out.println(65539 + SHARED_UNIT);

    }
}
