package com.stefan.DailyTest;

public class Node<E> {
    E item;
    Node<E> next;
    public Node(E e) {
        item = e;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public E getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "Node{" +
                "item=" + item +
                ", next=" + next +
                '}';
    }
}
