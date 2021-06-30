package com.stefan;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TestShutdownServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TestShutdownServlet doGet....");
    }

    static {
        System.out.println(TestShutdownServlet.class.getClassLoader());
        System.out.println(Thread.currentThread().getName());
        TestThread testThread = new TestThread();
        testThread.start();
        System.out.println("testThread.isDaemon()=" + testThread.isDaemon());
    }
}
