package com.stefan.docx4j;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;

import java.io.File;
import java.util.List;

/**
 * @author stefan
 * @date 2021/11/17 16:48
 */
public class Test {
    public static void main(String[] args) throws Docx4JException {
        String inputfilepath = "C:\\Users\\faisco\\Downloads\\jumpServer\\测试文档.docx";
        WordprocessingMLPackage wordMLPackage = Docx4J.load(new File(inputfilepath));
        MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
        List<Object> contentList =  mainDocumentPart.getContent();
        for (Object o : contentList) {
            org.docx4j.wml.P p = (org.docx4j.wml.P)o;
            System.out.println(p.getPPr().getSectPr());
        }
        ;
        System.out.println(mainDocumentPart.getPackage().getTitle());
    }
}
