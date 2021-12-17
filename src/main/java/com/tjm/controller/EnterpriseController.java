package com.tjm.controller;

import com.tjm.pojo.Enterprise;
import com.tjm.pojo.Product;
import com.tjm.service.EnterpriseService;
import com.tjm.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    //添加公司信息
    @RequestMapping(value = "/addEnterprise",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Enterprise enterprise){
        enterpriseService.insertEnterprise(enterprise);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }
}
