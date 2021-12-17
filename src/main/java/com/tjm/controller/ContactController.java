package com.tjm.controller;

import com.tjm.pojo.Contact;
import com.tjm.service.ContactService;
import com.tjm.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    //添加联系人信息
    @RequestMapping(value = "/addContact",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Contact contact){
        String uid = MathUtil.getRandom620(8);
        contact.setUid(uid);
        contactService.insertContact(contact);
        Map<String,String> res = new HashMap<>();
        res.put("uid",uid);
        return res;
    }
}
