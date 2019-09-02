package jvmLearning.managementLearning;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class Demo8 {
    public static void main(String[] args) {
        ThreadMXBean  threadMXBean = ManagementFactory.getThreadMXBean();
        //获取当前线程数
        System.out.println(threadMXBean.getThreadCount());
        //获取守护进程线程数
        System.out.println(threadMXBean.getDaemonThreadCount());
        //获取峰值线程数（最大线程数）
        System.out.println(threadMXBean.getPeakThreadCount());
        //获取正在已启动的线程数
        System.out.println(threadMXBean.getTotalStartedThreadCount());
        //获取当前线程总cpu时间
        System.out.println(threadMXBean.getCurrentThreadCpuTime());
        //获取当前线程cpu时间
        System.out.println(threadMXBean.getCurrentThreadUserTime());

        long[] ids = threadMXBean.getAllThreadIds();
        for (long id : ids) {
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(id);
            System.out.println(threadInfo.getBlockedCount());
            System.out.println(threadInfo.getBlockedTime());
            System.out.println(threadInfo.getWaitedCount());
            System.out.println(threadInfo.getWaitedTime());
        }




    }
}
