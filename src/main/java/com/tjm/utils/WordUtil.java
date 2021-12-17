package com.tjm.utils;

import com.aspose.words.License;

import java.io.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class WordUtil {

    /**
     * 获取license
     *
     * @return
     */
    private final static String outputPath = "/Users/tjm/IdeaProjects/Intelligent_traffic33/src/main/resources/pdf/";

    private static boolean getLicense() {
        boolean result = false;
        try {
            // 凭证
            String licenseStr =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "      <Product>Aspose.Words for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                            "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                            "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                            "  </Data>\n" +
                            "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                            "</License>";
            InputStream license = new ByteArrayInputStream(licenseStr.getBytes("UTF-8"));
            License asposeLic = new License();
            asposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
//            LOG.error("error:", e);
            System.out.println("出错！");
        }
        return result;
    }

    /**
     * Word 2 pdf.
     */
    public static void word2Pdf(String inputPath, String fileName) {
        FileOutputStream fileOS = null;
        // 验证License
        if (!getLicense()) {
            System.out.println("验证License失败！");
            return;
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(inputPath));
        } catch (FileNotFoundException e) {
            System.out.println("未找到该文件！");
        }
        File file = new File(outputPath);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            outputStream = new FileOutputStream(new File(outputPath + fileName));
            Document document = new Document(inputStream);
            document.save(outputStream, SaveFormat.PDF);
            System.out.println("转换成功");
        } catch (Exception e) {
            System.out.println("转换失败，请联系管理员处理");
        } finally {
            if (inputPath != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
