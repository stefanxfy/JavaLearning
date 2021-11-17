package com.stefan.docx4j;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.convert.out.common.writer.AbstractMessageWriter;
import org.docx4j.convert.out.html.SdtToListSdtTagHandler;
import org.docx4j.convert.out.html.SdtWriter;
import org.docx4j.fonts.BestMatchingMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author stefan
 * @date 2021/11/17 14:46
 */
public class WordToHtml {
    public static void main(String[] args) throws Exception {
        String inputfilepath = "C:\\Users\\faisco\\Downloads\\jumpServer\\测试文档.docx";
        WordprocessingMLPackage wordMLPackage = Docx4J.load(new File(inputfilepath));

        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();

//    	htmlSettings.setImageDirPath(inputfilepath + "_files");
//    	htmlSettings.setImageTargetUri(inputfilepath.substring(inputfilepath.lastIndexOf("/")+1)
//    			+ "_files");
        htmlSettings.setImageDirPath("");

        htmlSettings.setWmlPackage(wordMLPackage);

        String userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " +
                "{ margin: 0; padding: 0; border: 0;}" +
                "body {line-height: 1;} ";
        htmlSettings.setUserCSS(userCSS);
        SdtWriter.registerTagHandler("HTML_ELEMENT", new SdtToListSdtTagHandler());

        Mapper fontMapper = new BestMatchingMapper(); // better for Linux
        wordMLPackage.setFontMapper(fontMapper);
        htmlSettings.setFontMapper(fontMapper);

        OutputStream os = new FileOutputStream(inputfilepath + ".html");

        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
        System.out.println("Saved: " + inputfilepath + ".html ");

        // Clean up, so any ObfuscatedFontPart temp files can be deleted
        if (wordMLPackage.getMainDocumentPart().getFontTablePart()!=null) {
            wordMLPackage.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
        }
        // This would also do it, via finalize() methods
        htmlSettings = null;
        wordMLPackage = null;

    }
}
