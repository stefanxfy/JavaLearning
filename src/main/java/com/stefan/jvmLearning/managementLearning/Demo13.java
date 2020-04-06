package com.stefan.jvmLearning.managementLearning;

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
        String name = (String) mBeanServer.getAttribute(helloMxBeanObjectName, "Name");

        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(helloMxBeanObjectName);
        System.out.println("mBeanInfo-----------------");
        for (MBeanAttributeInfo mBeanInfoAttribute : mBeanInfo.getAttributes()) {
            System.out.println("name=" + mBeanInfoAttribute.getName());
            System.out.println("type=" + mBeanInfoAttribute.getType());
        }
        System.out.println("ClassName=" + mBeanInfo.getClassName());
        for (MBeanConstructorInfo constructor : mBeanInfo.getConstructors()) {
            System.out.println("constructor--name=" + constructor.getName());
            for (MBeanParameterInfo mBeanParameterInfo : constructor.getSignature()) {
                System.out.println("constructor--mBeanParameterInfo--type=" + mBeanParameterInfo.getType());
                System.out.println("constructor--mBeanParameterInfo--name=" + mBeanParameterInfo.getName());
            }
        }
        for (MBeanOperationInfo operation : mBeanInfo.getOperations()) {
            System.out.println("operation--returnType=" + operation.getReturnType());
            System.out.println("operation--name=" + operation.getName());
            for (MBeanParameterInfo mBeanParameterInfo : operation.getSignature()) {
                System.out.println("operation--mBeanParameterInfo--type=" + mBeanParameterInfo.getType());
                System.out.println("operation--mBeanParameterInfo--name=" + mBeanParameterInfo.getName());
            }

        }
        System.out.println("mBeanInfo-----------------");

        //调用方法
        Object[] perms = {"stefanxfy"};
        String[] signs = {"java.lang.String"};
        mBeanServer.invoke(helloMxBeanObjectName, "sayHello", perms, signs);

        Thread.sleep(100000000);
    }
}
