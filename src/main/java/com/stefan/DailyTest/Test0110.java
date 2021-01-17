package com.stefan.DailyTest;

import java.util.concurrent.ConcurrentHashMap;

public class Test0110 {
    public static void main(String[] args) {
        int hash = "shanghaiooo".hashCode();
//        int segmentShift = 28;
//        int segmentMask = 15;
//        int j = (hash >>> segmentShift) & segmentMask;
//        System.out.println(hash >>> segmentShift);
//        System.out.println(j);
//        System.out.println(47 & segmentMask);
//        System.out.println(Integer.toBinaryString(47));
//        System.out.println(Integer.toBinaryString(15));
//
//        System.out.println("---------------------");
//        System.out.println(Integer.toBinaryString(7));
//        System.out.println(Integer.toBinaryString(9));
//        System.out.println(9 & 7);
//        System.out.println(1 < 1);
        hash= 11;
        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(hash));
        System.out.println(hash);
        System.out.println(4 & hash);
        System.out.println(5 & hash);


    }
}
