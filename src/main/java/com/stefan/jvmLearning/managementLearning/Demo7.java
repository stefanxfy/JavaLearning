package com.stefan.jvmLearning.managementLearning;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

//获取操作系统的信息
public class Demo7 {
    public static void main(String[] args) {

//
//        for (Class<? extends PlatformManagedObject> platformManagementInterface : ManagementFactory.getPlatformManagementInterfaces()) {
//            System.out.println(platformManagementInterface.getName());
//        }

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        //获取jvm运行时参数列表
        for (String inputArgument : runtimeMXBean.getInputArguments()) {
            System.out.println(inputArgument);
        }
        System.out.println("-------------------------------------------");
        System.out.println(runtimeMXBean.getBootClassPath());
        System.out.println(runtimeMXBean.getClassPath());
        System.out.println(runtimeMXBean.getLibraryPath());

        System.out.println("-------------------------------------------");
        System.out.println("Name: " + runtimeMXBean.getName());
        System.out.println("ManagementSpecVersion: " + runtimeMXBean.getManagementSpecVersion());
        System.out.println("SpecName: " + runtimeMXBean.getSpecName());
        System.out.println("SpecVendor: " + runtimeMXBean.getSpecVendor());
        System.out.println("SpecVersion: " + runtimeMXBean.getSpecVersion());
        System.out.println("VmVendor: " + runtimeMXBean.getVmVendor());
        System.out.println("VmVersion: " + runtimeMXBean.getVmVersion());
        System.out.println("VmName: " + runtimeMXBean.getVmName());
        System.out.println("----------------------------------------");

        for (String key : runtimeMXBean.getSystemProperties().keySet()) {
            System.out.println(key + ":" + runtimeMXBean.getSystemProperties().get(key));
        }

    }
}
