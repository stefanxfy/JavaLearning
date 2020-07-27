package com.stefan;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
    private static volatile boolean isStop = false;
    public static void main( String[] args ) {
//        System.out.println( "Hello World!" );
//        //1586256985
//        SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
//        System.out.println(new Date(1586256985));
//        System.out.println(dt.format(new Date(1586256985 * 1000L)));;
        double d = 0.40;
        System.out.println(d > 0.5);
        int i = 1;
        int ii = 2;
        String iii = "aa";
        System.out.println(i + ii + iii);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    int i = 0;
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isStop = true;
    }
}
