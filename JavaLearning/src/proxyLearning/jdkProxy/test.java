package proxyLearning.jdkProxy;

import sun.misc.ProxyGenerator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class test {

	public static void main(String[] args) throws IOException {
		Star jay = new JayChou();
		 
        StarProxy proxy = new StarProxy(jay);
   
        Star realProxy = (Star) proxy.create();
        realProxy.dance();
        realProxy.sing();
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        test();
	}

	public static  void test() throws IOException {
        byte[] fileBytes = ProxyGenerator.generateProxyClass("RealProxy", JayChou.class.getInterfaces());
        ByteBuffer buffer = ByteBuffer.wrap(fileBytes);
        FileChannel fileChannel = FileChannel.open(Paths.get("classes/production/JavaLearning/proxyLearning/jdkProxy/RealProxy.class"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        fileChannel.write(buffer);
    }

}
