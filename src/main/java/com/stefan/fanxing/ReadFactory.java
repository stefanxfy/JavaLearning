package com.stefan.fanxing;

public class ReadFactory {
    public static Read getRead(int i) {
        if (i == 1) {
            return new YuWenRead();
        } else {
            return new ShuxueRead();
        }
    }
}
