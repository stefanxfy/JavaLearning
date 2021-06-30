package com.stefan.DailyTest;

//import com.stefan.TestShutdownServlet;

import java.io.IOException;
import java.net.Socket;

public class TestTomcatShutdown {
    static {

    }
    public static void main(String[] args) throws InterruptedException {
//        TestShutdownServlet testShutdownServlet = new TestShutdownServlet();
        Thread.sleep(5000);
        Socket socket = null;
        try {
            //C:\study\myStudy\JavaLearning\src\main\java\com\stefan
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
