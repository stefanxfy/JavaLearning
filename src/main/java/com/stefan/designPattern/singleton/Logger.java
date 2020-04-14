package com.stefan.designPattern.singleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public enum Logger {
    INSTATCE();
    private BufferedWriter writer;

    Logger() {
        try {
            writer = new BufferedWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String msg) {
        try {
            writer.write(msg);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
