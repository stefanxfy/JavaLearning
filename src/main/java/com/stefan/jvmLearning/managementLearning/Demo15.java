package com.stefan.jvmLearning.managementLearning;

import javax.management.*;
import javax.management.remote.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Demo15 {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(jmxServiceURL, null);
            MBeanServerConnection mBeanServerConnection = jmxc.getMBeanServerConnection();
            ObjectName objectName = new ObjectName("jvmLearning.managementLearning:type=hello");
            String name = (String) mBeanServerConnection.getAttribute(objectName, "Name");
            System.out.println(name);
            //只能调用非属性的方法
            mBeanServerConnection.invoke(objectName, "sayHello", null, null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
        } catch (MBeanException e) {
            e.printStackTrace();
        }
    }
}
