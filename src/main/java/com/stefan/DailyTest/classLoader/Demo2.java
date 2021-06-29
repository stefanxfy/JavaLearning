package com.stefan.DailyTest.classLoader;

public class Demo2 {
    public void getClassLoad() {
        System.out.println(getClass() + "是" + getClass().getClassLoader() + "加载的");
    }
}
