package com.stefan.DailyTest.html2doc;

import fai.comm.util.FileEx;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.awt.image.URLImageSource;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 * @author stefan
 * @date 2021/11/16 10:33
 */
public class Test3 {
    public static void main(String[] args) throws IOException {
        String base64Img = "data:image/png;base64";
        String htmlStr = FileEx.readTxtFile("C:\\Users\\faisco\\Downloads\\jumpServer\\心理咨询的基本要求.docx.html", "utf-8");
        Document doc = Jsoup.parse(htmlStr);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            String imgUrl = img.attr("src");
            if (imgUrl.startsWith(base64Img)) {
                continue;
            }
            System.out.println(imgUrl);
            String base64ImgUrl = urlToBase64(imgUrl);
            img.attr("src", base64ImgUrl);
        }
        System.out.println(doc.toString());
        // C:\Users\faisco\Downloads\zk\image1.jpeg
// //265370.s21i.faiusr.com/2/ABUIABACGAAg1q7MjAYo-8mkyQMwoAs4uAg!100x100.jpg

    }

    private static String replaceImg(String html) {
        String base64Img = "data:image/png;base64";
        Document doc = Jsoup.parse(html);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            String imgUrl = img.attr("src");
            if (imgUrl.startsWith(base64Img)) {
                continue;
            }
            System.out.println(imgUrl);
            String base64ImgUrl = urlToBase64(imgUrl);
            img.attr("src", base64ImgUrl);
        }
        return doc.toString();
    }

    public static String urlToBase64(String imgUrl) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] buffer = null;
        try {
            if (!imgUrl.startsWith("http")) {
                ByteBuffer byteBuffer = FileEx.readFile(imgUrl);
                String base64 = "data:image/png;base64," + new BASE64Encoder().encode(byteBuffer);
                return base64;
            }
            // 创建URL
            URL url = new URL(imgUrl);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            inputStream = conn.getInputStream();
            outputStream = new ByteArrayOutputStream();
            // 将内容读取内存中
            buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            buffer = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // 关闭outputStream流
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        String base64 = "data:image/png;base64," + new BASE64Encoder().encode(buffer);
        return base64;
    }
}
