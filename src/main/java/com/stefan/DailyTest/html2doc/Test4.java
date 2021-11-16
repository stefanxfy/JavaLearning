package com.stefan.DailyTest.html2doc;

import com.stefan.DailyTest.DocxConverter;
import fai.comm.util.FileEx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author stefan
 * @date 2021/11/16 14:31
 */
public class Test4 {
    public static void main(String[] args) throws Exception {
        DocxConverter.DocxPicturesManager picturesManager = new DocxConverter.DocxPicturesManager() {
            @Override
            public String savePicture(byte[] bytes, String imgName) {
                String path = "C:\\Users\\faisco\\Downloads\\zk\\" + imgName;
                FileEx.append(path, bytes);
                return path;
            }
        };
        FileInputStream inputStream = new FileInputStream("C:\\Users\\faisco\\Downloads\\zk\\test1.docx");
        byte[] data = DocxConverter.getFormatBytes(inputStream, picturesManager, "utf-8", "html");
        System.out.println(new String(data));
        FileEx.append("C:\\Users\\faisco\\Downloads\\zk\\test1.docx.html", data);
    }
}
