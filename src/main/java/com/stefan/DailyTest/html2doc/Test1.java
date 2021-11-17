package com.stefan.DailyTest.html2doc;

import fai.comm.util.FileEx;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 * @author stefan
 * @date 2021/11/15 17:37
 */
public class Test1 {
    public static void main(String[] args) throws FileNotFoundException {
        //创建 POIFSFileSystem 对象
        POIFSFileSystem poifs = new POIFSFileSystem();
        //获取DirectoryEntry
        DirectoryEntry directory = poifs.getRoot();
        //创建输出流
        OutputStream out = new FileOutputStream("D:\\mysoft\\faiscoGit\\myStudy\\docx4j\\sample-docs\\心理咨询的基本要求.docx.html.docx");
        try {
//            InputStream ins = new FileInputStream("C:\\Users\\faisco\\Downloads\\jumpServer\\心理咨询的基本要求.docx.html");
            String html = FileEx.readTxtFile("D:\\mysoft\\faiscoGit\\myStudy\\docx4j\\sample-docs\\心理咨询的基本要求.docx.html", "utf-8");
            //创建文档,1.格式,2.HTML文件输入流
            html = replaceImg(html);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(html.getBytes());
            directory.createDocument("WordDocument", byteArrayInputStream);
            //写入
            poifs.writeFilesystem(out);
            //释放资源
            out.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replaceImg(String html) {
        String base64Img = "data:image";
        Document doc = Jsoup.parse(html);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            String imgUrl = img.attr("src");
            if (imgUrl.startsWith(base64Img)) {
                continue;
            }
            if (imgUrl == "") {
                System.out.println("empty");
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
