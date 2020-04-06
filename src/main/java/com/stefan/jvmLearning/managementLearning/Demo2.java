package com.stefan.jvmLearning.managementLearning;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;

//CompilationMXBean
public class Demo2 {
    public static void main(String[] args) {
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        //获取编译器的名字
        System.out.println(compilationMXBean.getName());
        //获取总编译时长
        System.out.println(compilationMXBean.getTotalCompilationTime());
        //是否支持编译监控
        System.out.println(compilationMXBean.isCompilationTimeMonitoringSupported());
    }
}
