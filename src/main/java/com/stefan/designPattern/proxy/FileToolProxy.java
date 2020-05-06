package com.stefan.designPattern.proxy;

import java.io.IOException;

public class FileToolProxy implements Tool{
    private Tool tool;

    public FileToolProxy(Tool tool) {
        this.tool = tool;
    }

    @Override
    public void copy(String fromPath, String toPath) throws IOException {
        long s = System.currentTimeMillis();
        tool.copy(fromPath, toPath);
        long e = System.currentTimeMillis();
        System.out.println("copy cost time=" + (e - s));
    }
}
