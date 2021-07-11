package com.stefan.DailyTest;

import com.stefan.TestShutdownServlet;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestTomcatShutdown {
    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8005);
            String shutdown = "SHUTDOWN";
            socket.getOutputStream().write(shutdown.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
