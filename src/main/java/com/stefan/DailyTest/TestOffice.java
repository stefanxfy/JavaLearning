package com.stefan.DailyTest;

//import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
//import org.jodconverter.core.office.OfficeException;
//import org.jodconverter.core.office.OfficeManager;
//import org.jodconverter.core.office.OfficeUtils;
//import org.jodconverter.local.JodConverter;
//import org.jodconverter.local.LocalConverter;
//import org.jodconverter.local.office.LocalOfficeManager;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestOffice {
    public static void main(String[] args) throws InterruptedException, IOException {
//        LocalOfficeManager.Builder builder = LocalOfficeManager.builder();
//        builder.officeHome("C:\\Program Files (x86)\\OpenOffice 4");
//        builder.portNumbers(29210, 29211);
//        builder.killExistingProcess(true);
//        builder.taskExecutionTimeout(1000 * 60 * 5L);
//        builder.taskQueueTimeout(1000 * 60 * 5L);
//        builder.processTimeout(1000 * 60 * 5L);
//        builder.workingDir("D:\\tmp\\");
//        OfficeManager officeManager = builder.install().build();
//        officeManager.start();
//        System.out.println("officeManager start=" + officeManager.isRunning());
//
//        FileInputStream inputStream = new FileInputStream("D:\\Java高手-代码篇.doc");
//        byte[] bytes = new byte[1024 * 10];
//        inputStream.read(bytes);
//        ByteArrayInputStream baris = new ByteArrayInputStream(bytes);
//
//        File newFile = new File("D:\\tmp\\Java高手-代码篇202009111645.pdf");
//        newFile.createNewFile();
//        long s = System.currentTimeMillis();
//        System.out.println("convert satrt s=" + s);
//        JodConverter.convert(baris).as(DefaultDocumentFormatRegistry.DOC).to(newFile).execute();
//        long e = System.currentTimeMillis();
//
//        System.out.println("convert end ms=" + (s-e));
//
//
//        Thread.sleep(60000);

    }
}
