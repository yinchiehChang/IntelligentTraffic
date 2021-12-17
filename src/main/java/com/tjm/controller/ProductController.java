package com.tjm.controller;

import com.tjm.pojo.Product;
import com.tjm.service.ProductService;
import com.tjm.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    //添加平台信息
    @RequestMapping(value = "/addProduct",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Product product){
        String uid = MathUtil.getRandom620(8);
        product.setUid(uid);
        productService.insertProduct(product);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        res.put("uid",uid);
        return res;
    }
}
