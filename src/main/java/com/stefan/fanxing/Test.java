package com.stefan.fanxing;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class Test {
    public static void main(String[] args) {
        YuWenBook yuWenBook = new YuWenBook();
        Read<YuWenBook> read = ReadFactory.getRead(1);
        YuWenBook yuWenBook1 = read.getName(yuWenBook);
        System.out.println(yuWenBook1.toString());
    }
}
