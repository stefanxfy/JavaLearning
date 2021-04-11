package com.stefan.DailyTest;

public class Test320 {
    public static void main(String[] args) {
        System.out.println("2:"+ Integer.toBinaryString(2) + ":" +Integer.numberOfLeadingZeros(2));
        System.out.println("4:"+ Integer.toBinaryString(4) + ":" +Integer.numberOfLeadingZeros(4));
        System.out.println("8:"+ Integer.toBinaryString(8) + ":" +Integer.numberOfLeadingZeros(8));
        System.out.println("16:"+ Integer.toBinaryString(16) + ":" +Integer.numberOfLeadingZeros(16));

        System.out.println("n=2,resizeStamp=" + resizeStamp(2) + ",二进制:" + Integer.toBinaryString(resizeStamp(2)));
        System.out.println("n=4,resizeStamp=" + resizeStamp(4) + ",二进制:" + Integer.toBinaryString(resizeStamp(4)));
        System.out.println("n=8,resizeStamp=" + resizeStamp(8) + ",二进制:" + Integer.toBinaryString(resizeStamp(8)));
        System.out.println("n=16,resizeStamp=" + resizeStamp(16) + ",二进制:" + Integer.toBinaryString(resizeStamp(16)));

        System.out.println(1 << (RESIZE_STAMP_BITS - 1));
    }

    static final int resizeStamp(int n) {
        // numberOfLeadingZeros 的作用是获取n的1之后高位有多少个0
        // Integer.numberOfLeadingZeros(n)=31-m （m是n二进制1所在位）
        // 1 << (RESIZE_STAMP_BITS - 1) = 1 << 15 = 2^15
        //
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

    private static int RESIZE_STAMP_BITS = 16;

}
