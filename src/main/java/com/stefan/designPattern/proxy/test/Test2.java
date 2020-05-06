package com.stefan.designPattern.proxy.test;

import com.stefan.designPattern.proxy.BufferFileTool;
import com.stefan.designPattern.proxy.FileToolProxy;
import com.stefan.designPattern.proxy.Tool;

import java.io.IOException;

public class Test2 {
    public static void main(String[] args) throws IOException {
        Tool tool = new FileToolProxy(new BufferFileTool());
        tool.copy("c:/study/xxl-job原理理解.txt", "c:/study/xxl-job原理理解2.txt");
    }
}
