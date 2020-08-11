package com.stefan.designPattern.proxy;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Tool {
    void copy(String fromPath, String toPath) throws IOException;
    static String getName() {
        return "1234";
    }
}
