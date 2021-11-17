package com.stefan.DailyTest.html2doc;

import com.stefan.DailyTest.DocxConverter;
import fai.comm.util.FileEx;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author stefan
 * @date 2021/11/17 9:29
 */
public class TestDocx4 {

    public static void main(String[] args) throws Exception {
        String htmlStr = FileEx.readTxtFile("D:\\mysoft\\faiscoGit\\myStudy\\docx4j\\sample-docs\\心理咨询的基本要求.docx.html", "utf-8");

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        String html = "<html><head><title>Import me</title></head><body><p>Hello World!</p></body></html>";

        wordMLPackage.getMainDocumentPart().addAltChunk(AltChunkType.Xhtml, htmlStr.getBytes());
        wordMLPackage.save(new File("D:\\mysoft\\faiscoGit\\myStudy\\docx4j\\sample-docs\\心理咨询的基本要求.docx.html.docx"));
    }
}
