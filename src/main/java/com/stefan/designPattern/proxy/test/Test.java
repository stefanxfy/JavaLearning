package com.stefan.designPattern.proxy.test;

import com.stefan.designPattern.proxy.FileStreamTool;
import com.stefan.designPattern.proxy.FileStreamToolProxy2;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
//        Tool tool = new FileTool();
//        tool.copy("c:/study/xxl-job原理理解.txt", "c:/study/xxl-job原理理解2.txt");
//        Tool tool = new FileToolProxy(new FileTool());
//        tool.copy("c:/study/xxl-job原理理解.txt", "c:/study/xxl-job原理理解2.txt");
        FileStreamTool tool = new FileStreamToolProxy2();
        tool.copy("c:/study/xxl-job原理理解.txt", "c:/study/xxl-job原理理解2.txt");
    }
}
