package jvmLearning.managementLearning;

import javax.management.*;
import javax.management.modelmbean.ModelMBeanNotificationBroadcaster;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Demo13 {
    public static void main(String[] args) throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException, InstanceAlreadyExistsException, NotCompliantMBeanException, InvalidAttributeValueException, InterruptedException, IOException {
        HelloMBean helloMBean = new Hello();
        ObjectName helloMxBeanObjectName = new ObjectName(helloMBean.getObjectName());
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(helloMBean, helloMxBeanObjectName);
//        Attribute attribute = new Attribute("name", "stefan");
//        mBeanServer.setAttribute(helloMxBeanObjectName, attribute);
        //调用方法
        Object[] perms = {"stefanxfy"};
        String[] signs = {"java.lang.String"};
        mBeanServer.invoke(helloMxBeanObjectName, "sayHello", perms, signs);

//        Registry registry = LocateRegistry.createRegistry(1099);
//
//        //构造JMXServiceURL
//        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
//        //创建JMXConnectorServer
//        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mBeanServer);
//        //启动
//        cs.start();

        Thread.sleep(100000000);
    }
}
