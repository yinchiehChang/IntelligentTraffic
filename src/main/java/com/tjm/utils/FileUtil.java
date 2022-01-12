package com.tjm.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.tjm.pojo.Attachement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.shiro.util.Assert;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class FileUtil {

    /**
     * @param request
     * @param path_deposit 新增目录名 支持多级不存在目录
     * @param file         待上传文件
     * @param isRandomName 是否要基于文件名称重新编排名称
     * @return
     */
    public static Attachement uploadMaterials(HttpServletRequest request, String path_deposit, MultipartFile file, boolean isRandomName) {
        //上传
        try {
            String[] typeImg = {"docx", "zip", "txt", "doc"};

            if (file != null) {
                String origName = file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:" + origName);
                // 判断文件类型
                String type = origName.indexOf(".") != -1 ? origName.substring(origName.lastIndexOf(".") + 1, origName.length()) : null;
                if (type != null) {
                    boolean booIsType = false;
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType = true;
                        }
                    }
                    //类型正确
                    if (booIsType) {
                        //存放文件的路径
                        String path = "/src/main/resources/review_materials/";
                        System.out.print("文件上传的路径" + path);
                        //组合名称
                        String fileSrc = path + path_deposit;
                        //是否随机名称
                        if (isRandomName) {
                            //随机名规则：文件名+_CY+当前日期+8位随机数+文件后缀名
//                            origName=origName.substring(0,origName.lastIndexOf("."))+"_CY"+formateString(new Date())+
//                                    MathUtil.getRandom620(8)+origName.substring(origName.lastIndexOf("."));
                            origName = origName.substring(0, origName.lastIndexOf("."))
                                    + origName.substring(origName.lastIndexOf("."));
                        }
                        System.out.println("随机文件名：" + origName);
                        //判断是否存在目录
                        File targetFile = new File(fileSrc, origName);
                        if (!targetFile.exists()) {
                            targetFile.getParentFile().mkdirs();//创建目录
                        }

                        //上传
                        file.transferTo(targetFile);
                        //完整路径
                        System.out.println("完整路径:" + targetFile.getAbsolutePath());

                        //将存入文件路径存入数据库中
                        Attachement attachement = new Attachement(MathUtil.getRandom620(8), origName, targetFile.getAbsolutePath(), type);
                        return attachement;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param request
     * @param file         待文件
     * @param isRandomName 是否要基于图片名称重新编排名称
     * @return
     */
    public static Attachement uploadImage(HttpServletRequest request, MultipartFile file, boolean isRandomName) {
        //上传
        try {
            String[] typeImg = {"jpg", "gif", "png", "docx", "zip", "txt", "doc"};

            if (file != null) {
                String origName = file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:" + origName);
                // 判断文件类型
                String type = origName.indexOf(".") != -1 ? origName.substring(origName.lastIndexOf(".") + 1, origName.length()) : null;
                if (type != null) {
                    boolean booIsType = false;
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType = true;
                        }
                    }
                    //类型正确
                    if (booIsType) {
                        //存放文件的路径
                        String path = "/src/main/resources/static/images/";
                        System.out.print("文件上传的路径" + path);
                        //组合名称
                        String fileSrc = path;
                        //是否随机名称
                        if (isRandomName) {
                            //随机名规则：文件名+_CY+当前日期+8位随机数+文件后缀名
//                            origName=origName.substring(0,origName.lastIndexOf("."))+"_CY"+formateString(new Date())+
//                                    MathUtil.getRandom620(8)+origName.substring(origName.lastIndexOf("."));
                            origName = origName.substring(0, origName.lastIndexOf("."))
                                    + origName.substring(origName.lastIndexOf("."));
                        }
                        System.out.println("随机文件名：" + origName);
                        //判断是否存在目录
                        File targetFile = new File(fileSrc, origName);
                        if (!targetFile.exists()) {
                            targetFile.getParentFile().mkdirs();//创建目录
                        }

                        //上传
                        file.transferTo(targetFile);
                        //完整路径
                        System.out.println("完整路径:" + targetFile.getAbsolutePath());

                        //将存入文件路径存入数据库中
                        Attachement attachement = new Attachement(MathUtil.getRandom620(8), origName, targetFile.getAbsolutePath(), type);
                        return attachement;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Attachement uploadKBFile(HttpServletRequest request, String path_deposit, MultipartFile file, boolean isRandomName, String uploadPath) {
        //上传知识库文件
        try {
            String[] typeImg = {"pdf", "docx", "doc"};

            if (file != null) {
                String origName = file.getOriginalFilename();// 文件原名称
                String randomName = UUID.randomUUID().toString() + origName.substring(origName.lastIndexOf("."));
                System.out.println("上传的文件原名称:" + origName);
                // 判断文件类型
                String type = origName.indexOf(".") != -1 ? origName.substring(origName.lastIndexOf(".") + 1, origName.length()) : null;
                if (type != null) {
                    boolean booIsType = false;
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType = true;
                        }
                    }
                    //类型正确
                    if (booIsType) {
                        //存放文件的路径
                        String path = "D:\\WORKSPACE\\TrafficSystem\\Intelligent_traffic\\";
                        path = path + uploadPath;
                        System.out.println("文件上传的路径" + path);
                        //组合名称
                        String fileSrc = path + path_deposit;
                        System.out.println("随机文件名：" + randomName);
                        //判断是否存在目录
                        File targetFile = new File(fileSrc, randomName);
                        if (!targetFile.exists()) {
                            targetFile.getParentFile().mkdirs();//创建目录
                        }

                        //上传
                        file.transferTo(targetFile);
                        //完整路径
                        System.out.println("完整路径:" + targetFile.getAbsolutePath());

                        //将存入文件路径存入数据库中
                        Attachement attachement = new Attachement(MathUtil.getRandom620(8), origName, targetFile.getAbsolutePath(), type);
                        return attachement;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean removeKBFile(String path) {
        try {
            java.io.File file = new java.io.File(path);
            file.delete();
            return true;
        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 格式化日期并去掉”-“
     *
     * @param date
     * @return
     */
    public static String formateString(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String list[] = dateFormater.format(date).split("-");
        String result = "";
        for (int i = 0; i < list.length; i++) {
            result += list[i];
        }
        return result;
    }

    public static void uploadFile(HttpServletRequest request, String path_deposit, MultipartFile file, boolean isRandomName, String uploadPath) {
        //上传
        try {
            String[] typeImg = {"docx", "doc", "zip"};

            if (file != null) {
                String origName = file.getOriginalFilename();// 文件原名称
                System.out.println("上传的文件原名称:" + origName);
                // 判断文件类型
                String type = origName.indexOf(".") != -1 ? origName.substring(origName.lastIndexOf(".") + 1, origName.length()) : null;
                if (type != null) {
                    boolean booIsType = false;
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType = true;
                        }
                    }
                    //类型正确
                    if (booIsType) {
                        //存放文件的路径
                        String path = new FileSystemResource("").getFile().getAbsolutePath();
//                        System.out.println(path);
                        path = path + uploadPath;
//                        String path = "src/main/resources/word/quality/template";
                        System.out.println("文件上传的路径" + path);
                        //组合名称
                        String fileSrc = path + path_deposit;
                        //是否随机名称
                        if (isRandomName) {
                            //随机名规则：文件名+_CY+当前日期+8位随机数+文件后缀名
                            origName = origName.substring(0, origName.lastIndexOf(".")) + "_CY" + formateString(new Date()) +
                                    MathUtil.getRandom620(8) + origName.substring(origName.lastIndexOf("."));
                        }
//                        System.out.println("随机文件名：" + origName);
                        //判断是否存在目录
                        File targetFile = new File(fileSrc, origName);
                        if (!targetFile.exists()) {
                            targetFile.getParentFile().mkdirs();//创建目录
                        }
                        //上传
                        file.transferTo(targetFile);
                        //完整路径
                        System.out.println("完整路径:" + targetFile.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     *
     * @param templatePath word模板地址
     * @param temDir       生成临时文件存放地址
     * @param fileName     文件名
     * @param params       替换的参数
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(templatePath, "模板路径不能为空");
        Assert.notNull(temDir, "临时文件路径不能为空");
        Assert.notNull(fileName, "导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"), "word导出请使用docx格式");
        if (!temDir.endsWith("/")) {
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
