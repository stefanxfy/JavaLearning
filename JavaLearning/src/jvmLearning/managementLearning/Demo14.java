package jvmLearning.managementLearning;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Demo14 {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanException, AttributeNotFoundException, ReflectionException, InstanceNotFoundException, InvalidAttributeValueException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        HelloMBean helloMBean = new Hello();
        ObjectName objectName = new ObjectName(helloMBean.getObjectName());
        mBeanServer.registerMBean(helloMBean, objectName);
        mBeanServer.setAttribute(objectName, new Attribute("Name", "stefan"));
        try {
            LocateRegistry.createRegistry(9999);
            //URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
            JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
            System.out.println("begin rmi start");
            jcs.start();
            System.out.println("rmi start");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
