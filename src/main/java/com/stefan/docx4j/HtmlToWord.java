package com.stefan.docx4j;

import fai.comm.util.FileEx;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;

import java.io.File;

/**
 * @author stefan
 * @date 2021/11/17 15:39
 */
public class HtmlToWord {
    public static void main(String[] args) throws Exception {
        String htmlPath = "C:\\Users\\faisco\\Downloads\\jumpServer\\测试文档.docx.html";
        String htmlStr = FileEx.readTxtFile(htmlPath, "utf-8");

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        String html = "<html><head><title>Import me</title></head><body><p>Hello World!</p></body></html>";

        wordMLPackage.getMainDocumentPart().addAltChunk(AltChunkType.Html, htmlStr.getBytes());
        wordMLPackage.save(new File(htmlPath + ".docx"));
    }
}
