package com.stefan.DailyTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import fai.comm.util.FileEx;
import fai.comm.util.Param;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.w3c.dom.Document;

/**
 * @author stefan
 * @date 2021/9/1 11:48
 */
public class Word2Html {
    private final static String  tempPath = "C:\\Users\\faisco\\Downloads\\jumpServer";

    public static void main(String argv[]) throws IOException {
        ByteBuffer byteBuffer = FileEx.readFile("C:\\Users\\faisco\\Downloads\\jumpServer\\西方圣人蓝莓乳状蜂蜜产品介绍.docx");

        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(byteBuffer.array()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert(document, outputStream, null);


        String rt = new String(outputStream.toByteArray());
        FileEx.deleteFile("C:\\Users\\faisco\\Downloads\\jumpServer\\test.html");
        FileEx.append("C:\\Users\\faisco\\Downloads\\jumpServer\\test.html", rt);
    }
}
