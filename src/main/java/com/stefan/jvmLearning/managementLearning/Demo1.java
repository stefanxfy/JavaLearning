package com.stefan.jvmLearning.managementLearning;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
//ClassLoadingMXBean
public class Demo1 {
    public static void main(String[] args) {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        int loadClassCount = classLoadingMXBean.getLoadedClassCount();//获取类加载数量
        long totalLoadedClassCount  = classLoadingMXBean.getTotalLoadedClassCount();//获取总的类加载数量
        long unLoadedClassCount = classLoadingMXBean.getUnloadedClassCount();//获取未加载类的数量
        boolean isVerbose = classLoadingMXBean.isVerbose();//是否详细打印类加载情况，默认false
        System.out.println("loadClassCount=" + loadClassCount);
        System.out.println("totalLoadedClassCount=" + totalLoadedClassCount);
        System.out.println("unLoadedClassCount=" + unLoadedClassCount);
        System.out.println("isVerbose=" + isVerbose);
        classLoadingMXBean.setVerbose(true);
        System.out.println("-----------------------------------");
        System.out.println("loadClassCount=" + loadClassCount);
        System.out.println("totalLoadedClassCount=" + totalLoadedClassCount);
        System.out.println("unLoadedClassCount=" + unLoadedClassCount);
        System.out.println("isVerbose=" + isVerbose);

    }
}
