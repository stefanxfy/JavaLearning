package com.stefan.DailyTest;

public class LogTest {
    public static void log() {
        Throwable thb = new Throwable();
        StackTraceElement[] stacks = thb.getStackTrace();
        System.out.println(stacks.length);
        StackTraceElement elem = stacks[1];
        String className = elem.getClassName();
        String methodName = elem.getMethodName();
        int lineNumber = elem.getLineNumber();
        System.out.println("className:" + className);
        System.out.println("methodName:" + methodName);
        System.out.println("lineNumber:" + lineNumber);
    }
}
