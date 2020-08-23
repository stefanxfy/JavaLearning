package com.stefan.DailyTest;

import java.util.concurrent.ArrayBlockingQueue;

public class Test11 {
    public static void main(String[] args) {
        //1.基本数据结构
        //2.put、take
        //1.迭代器
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);
//        arrayBlockingQueue.put();
//        arrayBlockingQueue.take();
//        arrayBlockingQueue.drainTo()
        int k = 49;
        int parent = (k - 1) >>> 1;
        System.out.println(parent);
    }
}
