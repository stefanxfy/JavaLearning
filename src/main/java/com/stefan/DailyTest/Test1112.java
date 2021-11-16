package com.stefan.DailyTest;

import com.stefan.DailyTest.html2doc.TestHtml2doc;
import fai.comm.util.FileEx;
import fai.comm.util.Log;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stefan
 * @date 2021/11/12 9:53
 */
public class Test1112 {
    public static void main(String[] args) {
        String encoding = "UTF-8";
        String format = "html";
        String path = "C:\\Users\\faisco\\Downloads\\jumpServer\\temp.docx";
        ByteBuffer byteBuffer = FileEx.readFile(path);
        String content = readDocContent(byteBuffer.array(), 65, false);
//        System.out.println(content);
//        System.out.println(content);
//        FileEx.append(path + ".html", content);
        File toFile = new File("C:\\Users\\faisco\\Downloads\\jumpServer\\demo6.txt");
        FileEx.append(toFile, content);
//        System.out.println(formatContent.length());
    }

    public static String readDocContent(byte[] array, final int fileType, boolean lineFeed) {
        String encoding = "UTF-8";
        String format = "html";
        if (fileType == FileEx.Type.WORD) {// Doc预览
            PicturesManager picturesManager = new PicturesManager() {
                @Override
                public String savePicture(byte[] bytes, PictureType type,
                                          String imgName, float widthInches, float heightInches) {
                    return "";
                }
            };
            try {
                return DocConverter.getFormatSingleContent(new ByteArrayInputStream(array), picturesManager, encoding, format, lineFeed);
            } catch (OfficeXmlFileException e) {
                e.printStackTrace();
                return readDocContent(array, 65, lineFeed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (fileType == FileEx.Type.WORDX) {// Docx预览

            DocxConverter.DocxPicturesManager picturesManager = new DocxConverter.DocxPicturesManager() {
                @Override
                public String savePicture(byte[] bytes, String imgName) {
                    String path = "C:\\Users\\faisco\\Downloads\\zk\\" + imgName;
                    FileEx.append(path, bytes);
                    return path;
                }
            };
            try {
                return DocxConverter.getFormatContent(new ByteArrayInputStream(array), picturesManager, encoding, format);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getMSWordsCount(String context) {
        int words_count = 0;
        //中文单词
        String cn_words = context.replaceAll("[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]", "");
        int cn_words_count = cn_words.length();
//        System.out.println(cn_words);
        //非中文单词
        String non_cn_words = context.replaceAll("[^(a-zA-Z0-9`\\-=\';.,/~!@#$%^&*()_+|}{\":><?\\[\\])]", " ");
        System.out.println(non_cn_words);
        int non_cn_words_count = 0;
        String[] ss = non_cn_words.split(" ");
        for (String s : ss) {
            if (s.trim().length() != 0) non_cn_words_count++;
        }
//中文和非中文单词合计
        words_count = cn_words_count + non_cn_words_count;
//        ToolLog.d(ConstString.TAG, "汉字：" + cn_words_count + "\n\t字符：" + non_cn_words_count);
        return words_count;
    }

    private static String removeHtmlOfTxt(String content) {
        String textStr = "";
        try {
            String regExScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regExStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regExHtml = "<[^>]+>";

            Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
            Matcher mScript = pScript.matcher(content);
            content = mScript.replaceAll("");

            Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
            Matcher mStyle = pStyle.matcher(content);
            content = mStyle.replaceAll("");

            Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
            Matcher  mHtml = pHtml.matcher(content);
            content = mHtml.replaceAll("");

            textStr = content;
        } catch (Exception e) {
            Log.logStd(e, "removeHtmlOfTxt err");
        }
        // ^\\w+$
        textStr=textStr.replaceAll("(?m)[\\r\\n|\\n|\\s*]+", "");//去掉空白行
        textStr=textStr.replaceAll("(?m)&[a-zA-Z]*?;", "");//去掉特殊转移字符
        return textStr;
    }


}
