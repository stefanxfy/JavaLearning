package com.stefan.DailyTest;

import fai.comm.util.DocConverter;
import fai.comm.util.DocxConverter;
import fai.comm.util.Errno;
import fai.comm.util.FileEx;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.usermodel.PictureType;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

/**
 * @author stefan
 * @date 2021/9/1 10:37
 */
public class TestDocToHtml {
    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = FileEx.readFile("C:\\Users\\faisco\\Downloads\\jumpServer\\改.docx");
        String rt = getDocxHtml(byteBuffer.array(), 65);
/*        FileEx.deleteFile("C:\\Users\\faisco\\Downloads\\jumpServer\\test.html");
        FileEx.append("C:\\Users\\faisco\\Downloads\\jumpServer\\test.html", rt);*/
//        System.out.println(rt);
    }

    public static String getDocxHtml( byte[] array, final int fileType) throws Exception {
        String encoding = "UTF-8";
        String format = "html";
        final int app = 0;
        final boolean tmp = false;
        final int maxWidth = 1024;
        final int maxHeight = 1024;

        if (fileType == FileEx.Type.WORD) {// Doc预览
            PicturesManager picturesManager = new PicturesManager() {
                @Override
                public String savePicture(byte[] bytes, PictureType type,
                                          String imgName, float widthInches, float heightInches) {
                    return "";
                }
            };
            byte[] data = DocConverter.getFormatBytes(new ByteArrayInputStream(array), picturesManager, encoding, format);

            String result = new String(data, "utf-8");
            return result;
        } else if (fileType == FileEx.Type.WORDX) {// Docx预览

            DocxConverter.DocxPicturesManager picturesManager = new DocxConverter.DocxPicturesManager() {
                @Override
                public String savePicture(byte[] bytes, String imgName) {
                    return "";
                }
            };
            byte[] data = DocxConverter.getFormatBytes(new ByteArrayInputStream(array), picturesManager, encoding, format);
            String result = new String(data, "utf-8");
            return result;
        }
        return null;
    }
}
