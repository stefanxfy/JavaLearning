package com.stefan.DailyTest;

public class Test0307 {
    private static int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;


    public static void main(String[] args) {
        int initialCapacity = 32;
        initialCapacity = initialCapacity + (initialCapacity >>> 1) + 1;
        System.out.println(initialCapacity);
//        tableSizeFor(initialCapacity);
        System.out.println(Integer.toBinaryString(64).length());
        System.out.println((resizeStamp(64)<< RESIZE_STAMP_SHIFT) + 2);
        System.out.println((resizeStamp(64)<< RESIZE_STAMP_SHIFT));
        System.out.println(Integer.toBinaryString((resizeStamp(64)<< RESIZE_STAMP_SHIFT) + 2));
        System.out.println(Integer.toBinaryString((resizeStamp(32)<< RESIZE_STAMP_SHIFT) + 2));
        System.out.println(Integer.toBinaryString(resizeStamp(16)<< RESIZE_STAMP_SHIFT));
        System.out.println(Integer.toBinaryString(resizeStamp(8)<< RESIZE_STAMP_SHIFT));
        System.out.println(Integer.toBinaryString(resizeStamp(4)<< RESIZE_STAMP_SHIFT));
        System.out.println(Integer.toBinaryString(resizeStamp(2)<< RESIZE_STAMP_SHIFT));

        System.out.println(resizeStamp(64));
        System.out.println(resizeStamp(32));
        System.out.println(resizeStamp(16));

        System.out.println(resizeStamp(8));
        System.out.println(resizeStamp(4));
        System.out.println(resizeStamp(2));

        System.out.println(1 << 15);
        System.out.println(Integer.toBinaryString(1 << 15).length());
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(2)));
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(4)));
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(8)));
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(16)));
        System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(16)));

    }

    static final int resizeStamp(int n) {
        // numberOfLeadingZeros 的作用是获取n的1之后高位有多少个0
        // Integer.numberOfLeadingZeros(n)=31-m （m是n二进制1所在位）
        // 1 << (RESIZE_STAMP_BITS - 1) = 1 << 15 = 2^15
        //
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

    private static final int tableSizeFor(int c) {
        System.out.println(Integer.toBinaryString(c));
        int n = c - 1;
        System.out.println("n = c - 1:" + n + "," + Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println("n |= n >>> 1:" + n + "," + Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println("n |= n >>> 2:" + n + "," + Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println("n |= n >>> 4:" + n + "," + Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println("n |= n >>> 8:" + n + "," + Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println("n |= n >>> 16:" + n + "," + Integer.toBinaryString(n));
        System.out.println("n+1:" + (n+1) + "," + Integer.toBinaryString(n+1));
        return n+1;
    }
}
