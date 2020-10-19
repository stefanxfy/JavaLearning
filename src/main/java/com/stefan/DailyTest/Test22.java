package com.stefan.DailyTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Test22 {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
//        Runtime.getRuntime().exec();
        Integer i = threadLocal.get();
        System.out.println(i);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    System.out.println("-----------");
                }
            }
        });
        thread.start();
        thread.interrupt();
        List<Node<Integer>> list = new ArrayList<Node<Integer>>();
        list.add(new Node<Integer>(1));
        list.add(new Node<Integer>(2));
        list.add(new Node<Integer>(3));
        int sum = list.stream().mapToInt(Node<Integer>::getItem).sum();
        System.out.println(sum);

        int a = 1;
        int b = 2;
        int c = 3;
        a = b = c;
        System.out.println(a);
        System.out.println(b);
    }
}
