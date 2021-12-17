package com.tjm.utils;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseWordUtil {

    //解析表格
    public static List<String> readWord(File file) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            if (file.getAbsolutePath().endsWith(".doc") || file.getAbsolutePath().endsWith(".docx")) {
                XWPFDocument xdoc = null;
                xdoc = new XWPFDocument(new FileInputStream(file));
                //HWPFDocument xdoc = new HWPFDocument(new FileInputStream(new File(path)));

                List<XWPFTable> tables = xdoc.getTables();
                for (XWPFTable table : tables) {
                    // 获取表格的行
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        // 获取表格的每个单元格
                        List<XWPFTableCell> tableCells = row.getTableCells();
                        for (XWPFTableCell cell : tableCells) {
                            // 获取单元格的内容
                            String text1 = cell.getText();
//                            System.out.println(text1);
                            list.add(StringUtils.deleteWhitespace(text1));
                        }
                    }
                }
            } else {
//                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //解析页眉
    public static List<String> readHeader(File file) throws Exception{
        List<String> list = new ArrayList<>();
        try {
            if (file.getAbsolutePath().endsWith(".doc") || file.getAbsolutePath().endsWith(".docx")) {
                XWPFDocument xdoc = null;
                xdoc = new XWPFDocument(new FileInputStream(file));
                //HWPFDocument xdoc = new HWPFDocument(new FileInputStream(new File(path)));

                //页眉
                List<XWPFHeader> headerList = xdoc.getHeaderList();
                for (XWPFHeader xwpfHeader: headerList){
                    String[] lines = xwpfHeader.getText().split("\\r?\\n");
                    for (String line : lines) {
                        if (line != null && !line.isEmpty()) {
                            list.add(StringUtils.deleteWhitespace(line));
                        }
                    }
                }
            } else {
                System.out.println("此文件不是word文件！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析标题
    public static List<Map<String, String>> readTitle(String path) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            if (path.endsWith(".doc") || path.endsWith(".docx")) {
                XWPFDocument xdoc = null;
                xdoc = new XWPFDocument(new FileInputStream(new File(path)));
                //HWPFDocument xdoc = new HWPFDocument(new FileInputStream(new File(path)));

                //标题
                List<XWPFParagraph> paragraphs = xdoc.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    String text = paragraph.getParagraphText();
                    String style = paragraph.getStyle();
                    Map<String, String> map = new HashMap<>();

                    if ("1".equals(style)) {
//                        System.out.println(text + "--[" + style + "]");
                    } else if ("2".equals(style)) {
//                        System.out.println(text + "--[" + style + "]");
                    } else if ("3".equals(style)) {
//                        System.out.println(text + "--[" + style + "]");
                    } else if ("4".equals(style)) {
//                        System.out.println(text + "--[" + style + "]");
                    } else if ("5".equals(style)) {
//                        System.out.println(text + "--[" + style + "]");
                    } else if ("6".equals(style)) {
//                        System.out.println(text + "--[" + style + "]");
                    } else {
                        continue;
                    }

                    map.put("level", style);
                    map.put("title", text);
                    list.add(map);
                }
            } else {
                System.out.println("此文件不是word文件！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
