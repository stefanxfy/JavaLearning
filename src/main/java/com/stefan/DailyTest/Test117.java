package com.stefan.DailyTest;

public class Test117 {
    public static void main(String[] args) {
        String s = "北京";
        int c = 4;
        int c2 = 8;
        System.out.println(Integer.toBinaryString(spread(s.hashCode())));
        System.out.println(Integer.toBinaryString(c));
        System.out.println(spread(s.hashCode()) & c);
        System.out.println(spread(s.hashCode()) & (c-1));
        System.out.println(spread(s.hashCode()) & (c2-1));

    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

}
