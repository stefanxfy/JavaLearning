package com.stefan.DailyTest.html2doc;

import com.stefan.DailyTest.DocConverter;
import com.stefan.DailyTest.DocxConverter;
import fai.comm.util.FileEx;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * @author stefan
 * @date 2021/11/16 10:09
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        InputStream ins = new FileInputStream("C:\\Users\\faisco\\Downloads\\jumpServer\\心理咨询的基本要求.docx");
        XWPFDocument docx = new XWPFDocument();
    }
}
