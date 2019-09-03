package jvmLearning.managementLearning;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Demo13 {
    public static void main(String[] args) throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException, InstanceAlreadyExistsException, NotCompliantMBeanException, InvalidAttributeValueException, InterruptedException, IOException {
        HelloMBean helloMBean = new Hello();
        Chat stefan = new Chat();
        Hello2 hello2 = new Hello2();
        ObjectName stefanMxBeanObjectName = new ObjectName(stefan.getObjectName());

        ObjectName helloMxBeanObjectName = new ObjectName(helloMBean.getObjectName());
        //ObjectName的取值必须遵循domain:key=的命名格式，英文:前必须有一个名字，叫做domain名称，英文:后至少有一对key=value，key随意取名字
        ObjectName hello2MxBeanObjectName = new ObjectName("jvmLearning.managementLearning:flag=hello2");
        //Domain part must be specified
        //Key properties cannot be empty
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(helloMBean, helloMxBeanObjectName);
        mBeanServer.registerMBean(hello2, hello2MxBeanObjectName);
        mBeanServer.registerMBean(stefan, stefanMxBeanObjectName);
        //name值首字母必须大写
        Attribute attribute = new Attribute("Name", "stefan");
        mBeanServer.setAttribute(helloMxBeanObjectName, attribute);
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
