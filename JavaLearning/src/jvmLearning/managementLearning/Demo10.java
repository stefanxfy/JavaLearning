package jvmLearning.managementLearning;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

public class Demo10 {
    public static void main(String[] args) {
        List<MemoryPoolMXBean>  memoryPoolMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeanList) {

            System.out.println("memoryPoolMXBean name: " + memoryPoolMXBean.getName());
            System.out.println("MemoryType: " + memoryPoolMXBean.getType().name());

            System.out.println("CollectionUsageThreshold(垃圾回收内存使用阈值): " + memoryPoolMXBean.getCollectionUsageThreshold());
            System.out.println("CollectionUsageThresholdCount（达到垃圾回收内存使用阈值的次数）: " + memoryPoolMXBean.getCollectionUsageThresholdCount());
            String[] mmns = memoryPoolMXBean.getMemoryManagerNames();
            System.out.println("MemoryManagerNames: ");
            for (String mmn : mmns) {
                System.out.println(mmn);
            }
            System.out.println("最近垃圾回收之后，内存使用情况: ");
            MemoryUsage collectionUsage = memoryPoolMXBean.getCollectionUsage();
            System.out.println("Committed: " + collectionUsage.getCommitted());
            System.out.println("Init: " + collectionUsage.getInit());
            System.out.println("Max: " + collectionUsage.getMax());
            System.out.println("Used: " + collectionUsage.getUsed());

            System.out.println("内存使用情况估计值: ");
            MemoryUsage memoryUsage = memoryPoolMXBean.getUsage();
            System.out.println("Committed: " + memoryUsage.getCommitted());
            System.out.println("Init: " + memoryUsage.getInit());
            System.out.println("Max: " + memoryUsage.getMax());
            System.out.println("Used: " + memoryUsage.getUsed());

            System.out.println("峰值内存使用情况估计值: ");
            MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();
            System.out.println("Committed: " + peakUsage.getCommitted());
            System.out.println("Init: " + peakUsage.getInit());
            System.out.println("Max: " + peakUsage.getMax());
            System.out.println("Used: " + peakUsage.getUsed());

            System.out.println("---------------------------------------------------------------");
        }
    }
}
