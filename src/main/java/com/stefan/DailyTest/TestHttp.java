package com.stefan.DailyTest;
import cn.hutool.http.HttpUtil;
import fai.comm.util.Encoder;
import fai.comm.util.FaiUrlConnection;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URLEncoder;

/**
 * @author stefan
 * @date 2021/7/15 16:31
 */
public class TestHttp {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://172.16.3.98:7012/errDetailApi?msg=Uncaught TypeError: Cannot read property '_autoHeight' of undefined&url=" + Encoder.encodeUrl("https://g-1.ss.faisys.com/js/dist/site.min.js?v=123456") + "&row=1&col=39691";
//        url = Encoder.encodeUrl(url, "gbk");

        // http://172.16.3.98:7012/errDetailApi?msg=Uncaught%20TypeError:%20Cannot%20read%20property%20%27_autoHeight%27%20of%20undefined&url=https://g-1.ss.faisys.com/js/dist/site.min.js?v=121323i2332&row=1&col=39691
        System.out.println(url);
        String result = HttpUtil.get(url);
        System.out.println(result);

    }
}
