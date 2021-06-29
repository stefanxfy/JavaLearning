package com.stefan.DailyTest.classLoader;

import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        // 初始化TestClassLoader，被将加载TestClassLoader类的类加载器设置为TestClassLoader的parent
        TestClassLoader testClassLoader = new TestClassLoader(TestClassLoader.class.getClassLoader());
        System.out.println("TestClassLoader的父类加载器：" + testClassLoader.getParent());
        // 加载 Demo
        Class clazz = testClassLoader.loadClass("com.stefan.DailyTest.classLoader.Demo");
        System.out.println("Demo的类加载器：" + clazz.getClassLoader());
        Object o = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(o, null);

//        Thread.currentThread().setContextClassLoader();
    }
}
