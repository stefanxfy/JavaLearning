package jvmLearning.managementLearning;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Demo12 {
    private static final String MXBEAN_NAME_PREFIX = "java.nio:type=BufferPool,name=";
    private static final String MXBEAN_ATTR_MEMORY_USED = "MemoryUsed";
    private static final String MXBEAN_ATTR_TOTAL_CAPACITY = "TotalCapacity";
    public static void main(String[] args) throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException {
        ObjectName directMxBeanObjectName = new ObjectName(MXBEAN_NAME_PREFIX + "direct");
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        for (String domain : mBeanServer.getDomains()) {
            System.out.println(domain);
        }
        System.out.println("----------------------------");
        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(directMxBeanObjectName);
        System.out.println(mBeanInfo.getClassName());
        for (MBeanAttributeInfo attribute : mBeanInfo.getAttributes()) {
            Object val = mBeanServer.getAttribute(directMxBeanObjectName, attribute.getName());
            System.out.println(attribute.getName() + " : " + val);
        }
    }
}
