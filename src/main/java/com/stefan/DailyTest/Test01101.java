package com.stefan.DailyTest;

public class Test01101 {
    public static void main(String[] args) {
        int initialCapacity = 4;
        System.out.println(initialCapacity >>> 1);
        System.out.println(initialCapacity >>> 2);
        System.out.println(initialCapacity >>> 3);
//        System.out.println(initialCapacity + (initialCapacity >>> 1) + 1);
//        System.out.println(tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));

    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    // 1 * 2^30 = 1073741824
    private static final int MAXIMUM_CAPACITY = 1 << 30;
}
