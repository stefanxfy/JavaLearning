package jvmLearning.managementLearning;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Demo11 {
    private static final String MXBEAN_NAME_PREFIX = "java.nio:type=BufferPool,name=";
    private static final String MXBEAN_ATTR_MEMORY_USED = "MemoryUsed";
    private static final String MXBEAN_ATTR_TOTAL_CAPACITY = "TotalCapacity";
    public static void main(String[] args) throws MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        long directUsed = 0;
        long directTotalCapacity = 0;
        long mappedUsed = 0;
        long mappedTotalCapacity = 0;
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        System.out.println(mBeanServer.getDefaultDomain());
        for (String domain : mBeanServer.getDomains()) {
            System.out.println(domain);
        }
        // 获取DirectBuffer
        ObjectName directMxBeanObjectName = new ObjectName(MXBEAN_NAME_PREFIX + "direct");
        directUsed = (Long) mBeanServer.getAttribute(directMxBeanObjectName, MXBEAN_ATTR_MEMORY_USED);
        directTotalCapacity = (Long) mBeanServer.getAttribute(directMxBeanObjectName, MXBEAN_ATTR_TOTAL_CAPACITY);
        System.out.println(directUsed);
        System.out.println(directTotalCapacity);
        // 获取MappedBuffer
        ObjectName mappedMxBeanObjectName = new ObjectName(MXBEAN_NAME_PREFIX + "mapped");
        mappedUsed = (Long) mBeanServer.getAttribute(mappedMxBeanObjectName, MXBEAN_ATTR_MEMORY_USED);
        mappedTotalCapacity = (Long) mBeanServer.getAttribute(mappedMxBeanObjectName, MXBEAN_ATTR_TOTAL_CAPACITY);
        System.out.println(mappedUsed);
        System.out.println(mappedTotalCapacity);
    }
}
