package com.stefan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    private static volatile boolean isStop = false;
    public static void main( String[] args ) {
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
