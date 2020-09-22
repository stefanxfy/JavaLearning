package com.stefan.node;

import com.stefan.DailyTest.Node;

import java.util.concurrent.atomic.AtomicReference;

public class NodeThread implements Runnable{
    public static AtomicReference<Node<Integer>> tail = new AtomicReference<Node<Integer>>();
    private int i;
    public NodeThread(int i) {
        this.i = i;
    }
    @Override
    public void run() {
        Node<Integer> node = new Node<Integer>(i);
        Node<Integer> t = tail.get();
        if (tail.compareAndSet(t, node)) {
            if (t != null) {
                t.setNext(node);
            }
        }
        System.out.println("tail=" + t);
        System.out.println("tail.get() == node " + (tail.get() == node));
    }


}
