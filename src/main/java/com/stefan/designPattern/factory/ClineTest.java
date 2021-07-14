package com.stefan.designPattern.factory;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.concurrent.ThreadFactory;

public class ClineTest {
    public static void main(String[] args) {
        SimpleFactory.getInstance("A").doSomeThing();
        SimpleFactory.getInstance("B").doSomeThing();
        SimpleFactory.getInstance("ProductC").doSomeThing();
        String s = "";
        System.out.println(s == "");

        Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance();
    }


}
