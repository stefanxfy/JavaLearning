package jvmLearning.managementLearning;


//注册到MBeanServer的一个名称为xxx的bean必须实现一个以xxxMBean为名称的接口,且这个接口只能被这一个bean实现,且这个名为xxx的bean必须和xxxMBean的前缀名一致
//Domain part must be specified
//Key properties cannot be empty
//Exception in thread "main" javax.management.NotCompliantMBeanException: MBean class jvmLearning.managementLearning.Hello2 does not implement DynamicMBean, and neither follows the Standard MBean conventions (javax.management.NotCompliantMBeanException: Class jvmLearning.managementLearning.Hello2 is not a JMX compliant Standard MBean) nor the MXBean conventions (javax.management.NotCompliantMBeanException: jvmLearning.managementLearning.Hello2: Class jvmLearning.managementLearning.Hello2 is not a JMX compliant MXBean)
public class Hello2 implements Hello2MBean{
    public void sayHello() {
        System.out.println("hello");
    }

    public void sayHello(String name) {
        System.out.println("hello:" + name);
    }
}
