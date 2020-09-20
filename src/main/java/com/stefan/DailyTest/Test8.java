package com.stefan.DailyTest;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class Test8 {

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        long s = System.currentTimeMillis();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "172.16.3.7");
        properties.put("mail.smtp.auth", false);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.port", 10125);
        properties.put("mail.debug", true);
        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setSubject("哈哈哈哈测试", "utf-8");
        message.setContent("测试的点点滴滴\n计算法设计浪费时间\n东方嘉盛的方式来讲", "text/plain;charset=UTF-8");
        message.setSentDate(new Date());
        String[] FORM_ARR = new String[] {"faier@faidns.com", "faier1@faidns.com",
                "faier2@faidns.com", "faier3@faidns.com",
                "faier4@faidns.com", "faier5@faidns.com"};
        InternetAddress address = new InternetAddress(FORM_ARR[0], "faier");
        message.setFrom(address);
        message.setRecipients(Message.RecipientType.TO, "502681863@qq.com");
        long e1 = System.currentTimeMillis();
        System.out.println("1--" + (e1-s));

        Transport.send(message);
        long e = System.currentTimeMillis();
        System.out.println(e-s);

    }
}
