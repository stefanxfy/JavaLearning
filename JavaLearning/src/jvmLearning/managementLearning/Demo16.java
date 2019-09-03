package jvmLearning.managementLearning;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Demo16 {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InstanceNotFoundException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Chat stefan = new Chat();
        ObjectName objectName = new ObjectName(stefan.getObjectName());
        mBeanServer.registerMBean(stefan, objectName);
        mBeanServer.addNotificationListener(objectName, new HelloListener(), null, null);
        Thread.sleep(500000);
    }
}
