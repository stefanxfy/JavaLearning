package com.stefan.jvmLearning.managementLearning;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/** MinorGC & MajorGC 上次收集的状态 **/
//PS Scavenge   MinorGC
//PS MarkSweep  MajorGC
public class Demo3 {
    public static void main(String[] args) {
        List<GarbageCollectorMXBean> garbageCollectorMXBeanList =  ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeanList) {
            //gc名称
            System.out.println(garbageCollectorMXBean.getName());
            //收集次数
            System.out.println(garbageCollectorMXBean.getCollectionCount());
            //收集时长
            System.out.println(garbageCollectorMXBean.getCollectionTime());
            //内存池数组
            for (String memoryPoolName : garbageCollectorMXBean.getMemoryPoolNames()) {
                System.out.println(memoryPoolName);
            }
            System.out.println("-----------------------");
        }
    }
}
