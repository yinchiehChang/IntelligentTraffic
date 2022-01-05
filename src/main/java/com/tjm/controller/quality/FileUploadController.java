package com.tjm.controller.quality;

import com.tjm.pojo.Attachement;
import com.tjm.service.AttachementService;
import com.tjm.utils.FileUtil;
import org.apache.coyote.http11.filters.VoidInputFilter;
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
@RequestMapping(value = "/quality")
public class FileUploadController {

//    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

    @RequestMapping(value = "/uploadSpecification")
    @ResponseBody
    public Map<String,Object> uploadSpecification(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        String uploadPath = "D:\\WORKSPACE\\TrafficSystem\\Intelligent_traffic\\src\\main\\resources\\word\\quality\\template";
        String pathDeposit = "\\SoftwareRequirementsSpecification";
        Map<String, Object> resultMap = uploadFiles(request, response, file, uploadPath, pathDeposit);
        return resultMap;
    }

    @RequestMapping(value = "/uploadManual")
    @ResponseBody
    public Map<String,Object> uploadManual(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        String uploadPath = "D:\\WORKSPACE\\TrafficSystem\\Intelligent_traffic\\src\\main\\resources\\word\\quality\\template";
        String pathDeposit = "\\SoftwareManual\\";
        Map<String, Object> resultMap = uploadFiles(request, response, file, uploadPath, pathDeposit);
        return resultMap;
    }

    @RequestMapping(value = "/uploadCode")
    @ResponseBody
    public Map<String,Object> uploadCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        String uploadPath = "D:\\WORKSPACE\\TrafficSystem\\Intelligent_traffic\\src\\main\\resources\\word\\quality\\template";
        String pathDeposit = "\\CodeFile\\";
        Map<String, Object> resultMap = uploadFiles(request, response, file, uploadPath, pathDeposit);
        return resultMap;
    }

    @RequestMapping(value = "/upLoadWordParse")
    @ResponseBody
    public Map<String,Object> upLoadWordParse(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        String uploadPath = "D:\\WORKSPACE\\TrafficSystem\\Intelligent_traffic\\src\\main\\resources\\word\\quality\\template";
        String pathDeposit = "\\template-parse\\";
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
