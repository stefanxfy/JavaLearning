package com.stefan.DailyTest;

import com.google.gson.Gson;
import fai.comm.util.Encoder;
import fai.comm.util.FaiList;
import fai.comm.util.FaiUrlConnection;
import fai.comm.util.Param;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author stefan
 * @date 2021/9/10 11:29
 */
public class TestFaiList {
    public static void main(String[] args) {
/*        FaiList<Long> longs = new FaiList<Long>();
        longs.add(Long.MAX_VALUE);
        Param info = new Param();
        info.setList("list", longs);
        Param param = Param.parseParam(info.toJson());
        longs = param.getList("list");
        for (Long aLong : longs) {
            System.out.println(aLong);
        }
        System.out.println(Long.MAX_VALUE);
        System.out.println(longs);*/
        //
 /*       String remark = Encoder.encodeUrl("测试(本地同步至线上)");
        System.out.println(remark);
        String content = "{\n" +
                "  \"code\": 500,\n" +
                "  \"msg\": \"同步失败！\"\n" +
                "}";
        content = Encoder.encodeUrl(content);
        String url = " http://config.faisco.cn.faidev.cc/api/addConfig?config_name=TestSysncConfToOnline2&config_etc_type=4&config_file_type=1&type=1&remark="+ remark +"&sacct=stefan&content=" + content;

        String rt = FaiUrlConnection.getHttpContent(url);
        System.out.println(rt);*/

    }
    private static class ConfigHostNameApiCallback extends FaiUrlConnection.SocketCallbackFai {
        private String requestMethod = FaiUrlConnection.URL_CONNECTION_POST;
        public ConfigHostNameApiCallback() {
            this("config hostName api Invoke");
            this.setReadTimeout(6000);
            this.setConnectTimeout(6000);
        }
        public ConfigHostNameApiCallback(String business) {
            super(business);
        }

        @Override
        public void setHttpURLConnection(HttpURLConnection uc) throws IOException {
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setUseCaches(false);
        }

        @Override
        public String getCharset(HttpURLConnection uc) {
            return FaiUrlConnection.CHARSET_UTF8;
        }

        @Override
        public boolean isReponseContinue(int httpCode) {
            // 如果是500错误则继续读取http数据
            return httpCode == 500;
        }

        @Override
        public String getRequestMethod(HttpURLConnection uc) {
            return requestMethod;
        }
    }

}
