package com.stefan.DailyTest;

public class Test0127 {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        boolean stop = false;
        while (!stop) {
            System.out.println("stop=" + stop);
            stop = true;
            System.out.println("stop=" + stop);
            continue;
        }
    }
}
