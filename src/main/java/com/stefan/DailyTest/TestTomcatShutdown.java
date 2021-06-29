package com.stefan.DailyTest;

import java.io.IOException;
import java.net.Socket;

public class TestTomcatShutdown {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
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
