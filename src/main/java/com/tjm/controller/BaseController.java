package com.tjm.controller;

import com.tjm.pojo.Base;
import com.tjm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
   private BaseService baseService;

    /**
     *
     * 查询所有的知识库信息，同时将知识库下包括的安全等级、项目、测试项及测试点都返回
     * @return
     */
    @RequestMapping(value = "/queryAllBase",method = RequestMethod.GET)
    @ResponseBody
    public List<Base> queryAllBase(){
        List<Base> bases = baseService.queryBase();
        System.out.println("base=="+bases);
        return bases;
    }

}
