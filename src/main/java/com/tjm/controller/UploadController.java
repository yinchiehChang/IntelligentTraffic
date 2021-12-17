package com.tjm.controller;

import com.tjm.pojo.Attachement;
import com.tjm.service.AttachementService;
import com.tjm.utils.FileUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class UploadController {

    @Autowired
    AttachementService attachementService;

    @RequestMapping(value = "/UpLoad/review_materials")
    @ResponseBody
    public Map<String,Object> uploadMaterials(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", 400);
        if(file!=null&&file.length>0){
            //组合image名称，“;隔开”
            List<String> fileName =new ArrayList<String>();
            PrintWriter out = null;
            List<String> attachmentIds = new ArrayList<String>();
            try {
                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        //上传文件，随机名称，";"分号隔开
                        Attachement attachement = FileUtil.uploadMaterials(request, FileUtil.formateString(new Date())+"/", file[i], true);
                        fileName.add(attachement.getUri());
                        attachmentIds.add(attachement.getUid());
                        attachementService.insertAttachement(attachement);
                    }
                }
                //上传成功
                if(fileName!=null&&fileName.size()>0){
                    System.out.println("上传成功！");
                    resultMap.put("status", 200);
                    resultMap.put("message", "上传成功！");
                    resultMap.put("attachmentIds",attachmentIds);
                }else {
                    resultMap.put("status", 500);
                    resultMap.put("message", "上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("status", 500);
                resultMap.put("message", "上传异常！");
            }
        }else {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
        }
        return resultMap;
    }
//
//    @RequestMapping(value = "/UpLoad")
//    @ResponseBody
//    public Map<String,Object> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
//        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
//        resultMap.put("status", 400);
//        if(file!=null&&file.length>0){
//            //组合image名称，“;隔开”
//            List<String> fileName =new ArrayList<String>();
//            PrintWriter out = null;
//            List<String> attachmentIds = new ArrayList<String>();
//            try {
//                for (int i = 0; i < file.length; i++) {
//                    if (!file[i].isEmpty()) {
//                        //上传文件，随机名称，";"分号隔开
//                        Attachement attachement = FileUtil.uploadImage(request, "/upload/"+ FileUtil.formateString(new Date())+"/", file[i], true);
//                        fileName.add(attachement.getUri());
//                        attachmentIds.add(attachement.getUid());
//                        attachementService.insertAttachement(attachement);
//                    }
//                }
//                //上传成功
//                if(fileName!=null&&fileName.size()>0){
//                    System.out.println("上传成功！");
//                    resultMap.put("status", 200);
//                    resultMap.put("message", "上传成功！");
//                    resultMap.put("attachmentIds",attachmentIds);
//                }else {
//                    resultMap.put("status", 500);
//                    resultMap.put("message", "上传失败！文件格式错误！");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                resultMap.put("status", 500);
//                resultMap.put("message", "上传异常！");
//            }
//        }else {
//            resultMap.put("status", 500);
//            resultMap.put("message", "没有检测到有效文件！");
//        }
//        return resultMap;
//    }

    @RequestMapping(value = "/TestCases/upLoadWordParse")
    @ResponseBody
    public Map<String,Object> upLoadWordParse(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        String uploadPath = "/src/main/resources/word/testCases";
        String pathDeposit = "/template-parse/";
        Map<String, Object> resultMap = uploadFiles(request, response, file, uploadPath, pathDeposit);
        return resultMap;
    }

    private Map<String, Object> uploadFiles(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("file") MultipartFile[] file,
                                            String uploadPath, String pathDeposit) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", 400);
        if(file!=null&&file.length>0){
            //组合image名称，“;隔开”
            List<String> fileName =new ArrayList<String>();
            PrintWriter out = null;
            try {
                for (int i = 0; i < file.length; i++) {
                    if (!file[i].isEmpty()) {
                        //上传文件，随机名称，";"分号隔开
                        FileUtil.uploadFile(request, pathDeposit, file[i], false, uploadPath);
                    }
                }
                //上传成功
                if(fileName!=null&&fileName.size()>0){
                    System.out.println("上传成功！");
                    resultMap.put("status", 200);
                    resultMap.put("message", "上传成功！");
                }else {
                    resultMap.put("status", 500);
                    resultMap.put("message", "上传失败！文件格式错误！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("status", 500);
                resultMap.put("message", "上传异常！");
            }
        }else {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
        }
        return resultMap;
    }
}
