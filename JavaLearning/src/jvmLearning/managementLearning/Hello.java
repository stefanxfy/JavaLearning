package jvmLearning.managementLearning;

public class Hello implements HelloMBean {
    private String name;
    private final String objectName = "jvmLearning.managementLearning:type=hello";

    @Override
    public String getObjectName() {
        return objectName;
    }

    public Hello() {
    }

    @Override
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public void sayHello(String name) {
        System.out.println("hello:" + name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
