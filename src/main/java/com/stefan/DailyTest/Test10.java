package com.stefan.DailyTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * 1. FutureTask 实现于Runnable、Future
 * 2. ScheduledFutureTask继承自FutureTask，多实现了一个Delayed接口
 * 3. 延时阻塞队列
 */
public class Test10 {
    public static void main(String[] args) throws InterruptedException {
//        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
//        executor.execute();
//        executor.schedule()
//        executor.scheduleAtFixedRate();
//        executor.scheduleWithFixedDelay();
//        Map<String, Integer> map = new HashMap<>();
//        int i = map.get("1");
//        System.out.println(i);

        String s = "sds;dvfvf;sfcx;";
        System.out.println(s.substring(0,s.length() - 1));
//        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//        readWriteLock.readLock().lock();
//        readWriteLock.writeLock().lock();
        Boolean b = null;
        boolean bb = b;
        System.out.println(bb);
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
//        linkedBlockingQueue.put();
//        linkedBlockingQueue.offer();
//        linkedBlockingQueue.take();
//        linkedBlockingQueue.poll();
//        linkedBlockingQueue.peek();
//        linkedBlockingQueue.add()
        Node<String> node = new Node<String>("12");
        Node<String> node2 = node;
        node.item = null;
        System.out.println(node2.item);
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);
        arrayBlockingQueue.put("1");
        arrayBlockingQueue.put("2");
        arrayBlockingQueue.put("3");
        Iterator iterator = arrayBlockingQueue.iterator();
        System.out.println(arrayBlockingQueue.take());
        System.out.println(iterator.next());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(iterator.next());
        //在使用迭代器遍历时，出队列不会报ConcurrentModificationException，
        // 因为它是线程安全的
    /*    ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator iterator = list.iterator();
        System.out.println(iterator.next());
        list.remove(2);
        System.out.println(iterator.next());*/
        //ConcurrentModificationException
        linkedBlockingQueue.iterator();
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<Integer>();
        priorityBlockingQueue.put(1);

    }
}
