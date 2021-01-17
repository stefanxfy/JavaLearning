package com.stefan.DailyTest;

public class Test1228 {
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
//        System.out.println(MAXIMUM_CAPACITY);
        int initialCapacity = 33;
        int sshift = 0;
        int ssize = 1;
        while (ssize < 15) {
            ++sshift;
            ssize = ssize  << 1;
//            System.out.println(ssize);
        }
//        System.out.println("---------");
//        System.out.println(ssize);
//        System.out.println(sshift);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        int c = initialCapacity / ssize;
        System.out.println(c);
        if (c * ssize < initialCapacity)
            ++c;
        System.out.println(c);
        int cap = 2;
        while (cap < c)
            cap <<= 1;
        System.out.println(cap);
    }
}
