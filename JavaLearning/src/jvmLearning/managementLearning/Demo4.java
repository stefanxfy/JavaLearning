package jvmLearning.managementLearning;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

//MemoryUsage
//    private final long init; 初始字节数
//    private final long used; 已使用字节数
//    private final long committed; 已提交字节数
//    private final long max; 最大字节数
public class Demo4 {
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean =  ManagementFactory.getMemoryMXBean();
        //获取正在等待完成对象的数量
        System.out.println(memoryMXBean.getObjectPendingFinalizationCount());
        //类似于System.gc();
        memoryMXBean.gc();

        System.out.println("-----------heapMemoryUsage---------------");
        //获取堆内存信息
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        //获取堆内存已提交字节数
        System.out.println(heapMemoryUsage.getCommitted());
        //获取堆内存初始化字节数
        System.out.println(heapMemoryUsage.getInit());
        //获取堆内存最大字节数
        System.out.println(heapMemoryUsage.getMax());
        //获取堆内存已使用字节数
        System.out.println(heapMemoryUsage.getUsed());

        System.out.println("-----------nonHeapMemoryUsage---------------");
        //获取非堆内存信息
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        //获取非堆内存已提交字节数
        System.out.println(nonHeapMemoryUsage.getCommitted());
        //获取非堆内存初始化字节数
        System.out.println(nonHeapMemoryUsage.getInit());
        //获取非堆内存最大字节数
        System.out.println(nonHeapMemoryUsage.getMax());
        //获取非堆内存已使用字节数
        System.out.println(nonHeapMemoryUsage.getUsed());



//        List<MemoryManagerMXBean> memoryManagerMXBeanList =  ManagementFactory.getMemoryManagerMXBeans();
    }
}
