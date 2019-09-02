package jvmLearning.managementLearning;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.util.List;

//MemoryManagerMXBean
public class Demo5 {
    public static void main(String[] args) {
        List<MemoryManagerMXBean> memoryManagerMXBeanList =  ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean memoryManagerMXBean : memoryManagerMXBeanList) {
            //获取内存管理的名字
            System.out.println("memoryManagerMXBean name；" + memoryManagerMXBean.getName());
            System.out.println("getMemoryPoolNames----");
            for (String memoryPoolName : memoryManagerMXBean.getMemoryPoolNames()) {
                System.out.println(memoryPoolName);
            }
            System.out.println("----------------------------------------");
        }

    }
}
