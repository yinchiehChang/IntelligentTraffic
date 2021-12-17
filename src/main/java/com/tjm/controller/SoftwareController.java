package com.tjm.controller;

import com.tjm.config.Log;
import com.tjm.config.OperLog;
import com.tjm.pojo.Document_audit;
import com.tjm.pojo.Software;
import com.tjm.service.SoftwareService;
import com.tjm.utils.MathUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tjm.utils.FileUtil.formateString;

@Controller
public class SoftwareController {

    @Autowired
    SoftwareService softwareService;

    //根据审核材料id获取全部软件列表
    @RequestMapping("/SoftwareList")
    @ResponseBody
    public List<Software> softwares(int id){
        List<Software> softwareList = softwareService.getSoftwareList(id);
        System.out.println(softwareList);
        return softwareList;
    }


    //软件信息提交
    @RequestMapping(value = "/test_add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitTest(Software software){
        String software_uid = MathUtil.getRandom620(8);
        software.setSoftware_uid(software_uid);
        softwareService.insertSoftware(software);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    //软件信息提交
    @RequestMapping(value = "/software_edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> softwareEdit(Software software){
        softwareService.updateSoftware(software);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    //删除软件
    @GetMapping(value = "/delSoftware/{software_uid}")
    public String delSoftware(@PathVariable("software_uid") String software_uid){
        softwareService.delSoftware(software_uid);
        return "redirect:/test_task.html";
    }
}
