package com.stefan.jvmLearning.managementLearning;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

public class Demo6 {
    public static void main(String[] args) {
        List<MemoryPoolMXBean> memoryPoolMXBeanList =  ManagementFactory.getMemoryPoolMXBeans();

        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeanList) {
            MemoryUsage memoryUsage = memoryPoolMXBean.getCollectionUsage();
            memoryPoolMXBean.getName();
//            System.out.println(memoryUsage.getUsed());
//            System.out.println(memoryUsage.getMax());
//            System.out.println(memoryUsage.getCommitted());
//            System.out.println(memoryUsage.getInit());

        }
    }
}
