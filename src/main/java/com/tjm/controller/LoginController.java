package com.tjm.controller;

import com.tjm.config.Log;
import com.tjm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    @Log
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/users/login")
    @Log
    public String login(String username,String password,Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //执行登录方法，如果没有异常，则ok
        try {
            subject.login(token);
            return "index";
        }catch(UnknownAccountException e){//用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException ice){//密码错误
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    //注销
    @RequestMapping("/user/logout")
    @Log
    public String logout(HttpSession session){
        //将session注销
        session.invalidate();
        return "login";
    }

    //未授权
    @RequestMapping("/noauth")
    @ResponseBody
    @Log
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }

}
