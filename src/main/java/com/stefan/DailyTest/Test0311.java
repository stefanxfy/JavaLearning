package com.stefan.DailyTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Test0311 {
    public static void main(String[] args) {
//        int t = tableSizeFor(1);
//
//        System.out.println(Integer.toBinaryString(0 & ~2));
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(4);
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        map.put("7", "1");

        ConcurrentHashMap<String, String> map2 = new ConcurrentHashMap<>(2);
        map.compute("32", new BiFunction() {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        });
        System.out.println(map);

        System.out.println(HASH_BITS);
        System.out.println(HASH_BITS+1);

        System.out.println(Integer.toBinaryString(HASH_BITS));
    }

    static final int resizeStamp(int n) {
        // numberOfLeadingZeros 的作用是获取n的1之后高位有多少个0
        // Integer.numberOfLeadingZeros(n)=31-m （m是n二进制1所在位）
        // 1 << (RESIZE_STAMP_BITS - 1) = 1 << 15 = 2^15
        //
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        System.out.println("c-1:" + Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println("n >>> 1:" + Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println("n >>> 2:" + Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println("n >>> 4:" + Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println("n >>> 8:" + Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println("n >>> 16:" + Integer.toBinaryString(n));
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    private static int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
    private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

}
