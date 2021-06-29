package com.stefan.DailyTest.classLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Demo {
    Thread thread = new Thread();
    public void hello() {
        System.out.println(123);
        try {
            Class demo2 = Class.forName("com.stefan.DailyTest.classLoader.Demo2");
            Object o = demo2.newInstance();
            Method method = demo2.getMethod("getClassLoad", null);
            method.invoke(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
