package com.stefan.DailyTest;

import java.util.concurrent.TimeUnit;

public class Test0202 {
    public static void main(String[] args) throws InterruptedException {
        String s = "xxx_dddd_";
        System.out.println(s.substring(0, s.length()-1));
        String[] ss = {"xxx", "fff"};
        System.out.println(ss[1]);
        System.out.println(50 << 10 << 10);

        String x = "xxx\nddd\n";
        String[] xarr = x.split("\n");
        System.out.println(xarr[xarr.length-1]);
        TimeUnit.SECONDS.sleep(10000);
    }
}
