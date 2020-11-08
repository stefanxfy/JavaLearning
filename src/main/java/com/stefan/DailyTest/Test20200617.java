package com.stefan.DailyTest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Test20200617 {
    public static void main(String[] args) {
//        try (FileInputStream inputStream = new FileInputStream("a.txt")) {
//            inputStream.read();
//            inputStream.close();
//            BufferedInputStream bufferedInputStream = null;
//            bufferedInputStream.close();
//        } catch (Exception e) {
//
//        }
        int size = 10;
        int i = size >>> 1;
        System.out.println(i);
    }
}
