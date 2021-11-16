package com.stefan.DailyTest;

import fai.comm.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stefan
 * @date 2021/11/12 12:01
 */
public class HtmlUtil {

    public static String removeHtmlOfTxt(String content, boolean lineFeed) {
        String textStr = "";
        try {
            Matcher mScript = P_SCRIPT.matcher(content);
            content = mScript.replaceAll("");

            Matcher mStyle = P_STYLE.matcher(content);
            content = mStyle.replaceAll("");

            Matcher  mHtml = P_HTML.matcher(content);
            content = mHtml.replaceAll("");

            Matcher  mLF = P_LF.matcher(content);

            if (lineFeed) {
                content = mLF.replaceAll("CRLF");
            } else {
                content = mLF.replaceAll("");
            }

            Matcher  mBlank = P_BLANK.matcher(content);
            content = mBlank.replaceAll("");

            Matcher  mEsc = P_ESC.matcher(content);
            content = mEsc.replaceAll("");

            if (lineFeed) {
                // 段落间需要有一个换行，曲线救国
                Matcher  mCRLF = P_CRLF.matcher(content);
                content = mCRLF.replaceAll("\n");
                if (content.startsWith("\n")) {
                    content = content.substring("\n".length());
                }
            }
            textStr = content;
        } catch (Exception e) {
            Log.logStd(e, "removeHtmlOfTxt err");
        }
        return textStr;
    }

    private static final String REG_EX_SCRIPT = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
    private static final String REG_EX_STYLE = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
    private static final String REG_EX_HTML = "<[^>]+>";
    private static final String REG_EX_LF = "[\\r\\n|\\n|\\r|\\n\\r]+"; //空白行
    private static final String REG_EX_BLANK = "[\\s]+"; //空白
    private static final String REG_EX_ESC = "&[a-zA-Z]*?;";//特殊转移字符
    private static final String REG_EX_CRLF = "[CRLF]+";//中间换行符


    private static final Pattern P_SCRIPT = Pattern.compile(REG_EX_SCRIPT, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_STYLE = Pattern.compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_HTML = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_BLANK = Pattern.compile(REG_EX_BLANK, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_LF = Pattern.compile(REG_EX_LF, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_ESC = Pattern.compile(REG_EX_ESC, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_CRLF = Pattern.compile(REG_EX_CRLF, Pattern.CASE_INSENSITIVE);


}
