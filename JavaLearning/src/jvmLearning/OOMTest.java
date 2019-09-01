package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by stefan
 * Date on 2019-08-04  20:02
 * Convertion over Configuration!
 */
public class OOMTest {
    static class OOMObject {public byte[] placeholder = new byte[64 * 1024];
    }
    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            //稍作延时，令监视曲线的变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        System.gc();

        Thread.sleep(100000);
    }
}
