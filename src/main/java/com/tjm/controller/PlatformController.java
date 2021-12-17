package com.tjm.controller;

import com.tjm.pojo.Platform;
import com.tjm.service.PlatformService;
import com.tjm.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    //添加平台信息
    @RequestMapping(value = "/addPlatform",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Platform platform){
        String uid = MathUtil.getRandom620(8);
        platform.setUid(uid);
        platformService.insertPlatform(platform);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        res.put("uid",uid);
        return res;
    }
}
