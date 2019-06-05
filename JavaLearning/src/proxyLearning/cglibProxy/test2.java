package proxyLearning.cglibProxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class test2 {
    public static void main(String[] args) {
        //生成代理类的class路径，必须放在enhancer创建之前
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "src/proxyLearning/cglibProxy");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        //设置回调过滤器
        enhancer.setCallbackFilter(new StudentCallbackFilter());
        //NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
        Callback[] callbacks = {new Moniter(), NoOp.INSTANCE, new StudyCommittee()};
        enhancer.setCallbacks(callbacks);
        Student studentProxy = (Student) enhancer.create();
        studentProxy.giveMoney();
        System.out.println("--------------------");
        studentProxy.findTeacher();
        System.out.println("--------------------");
        studentProxy.giveHomeWork();
    }
}
