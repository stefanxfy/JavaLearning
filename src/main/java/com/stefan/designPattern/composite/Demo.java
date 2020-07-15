package com.stefan.designPattern.composite;


public class Demo {
    public static void main(String[] args) {
        CDirectory fileSystemTree = new CDirectory("C:\\study\\composite");
        CDirectory node_a = new CDirectory("C:\\study\\composite\\a");
        CDirectory node_b = new CDirectory("C:\\study\\composite\\b");
        CDirectory node_ac = new CDirectory("C:\\study\\composite\\a\\c");
        CDirectory node_bc = new CDirectory("C:\\study\\composite\\b\\c");

        fileSystemTree.addSubNode(node_a);
        fileSystemTree.addSubNode(node_b);
        node_a.addSubNode(node_ac);
        node_b.addSubNode(node_bc);
        CFile node_a_a = new CFile("C:\\study\\composite\\a\\a.txt");
        CFile node_b_b = new CFile("C:\\study\\composite\\b\\b.txt");
        node_a.addSubNode(node_a_a);
        node_b.addSubNode(node_b_b);

        System.out.println("tree num:" + fileSystemTree.countNumOfFiles());
        System.out.println("node_b num:" + node_b.countNumOfFiles());
        System.out.println("node_b num:" + node_b.countSizeOfFiles());

    }
}