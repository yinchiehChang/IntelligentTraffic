package com.tjm.controller;

import com.tjm.config.Log;
import com.tjm.config.OperLog;
import com.tjm.pojo.*;
import com.tjm.service.RoleService;
import com.tjm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //查询所有用户，返回列表界面
    @RequestMapping("/users")
    @Log
    @OperLog(operModul = "用户管理",operType = "查询",operDesc = "查询所有用户")
    public String list(Model model){
        Collection<Sys_User> users = userService.queryUserList();
        List<String> role_names = roleService.queryRole_names();
        //将结果放在请求中
        model.addAttribute("users",users);
        model.addAttribute("role_names",role_names);
        return "person";
    }

    //添加用户
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    @Log
    @OperLog(operModul = "用户管理",operType = "新增",operDesc = "新增用户")
    public Map<String,Object> add(Sys_User user){
        userService.addUser(user);
        Sys_User sys_user = userService.queryUserByName(user.getUser_name());
        Map<String,Object> res = new HashMap<>();
        res.put("user",sys_user);
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    //删除用户
    @GetMapping("/deluser/{id}")
    @Log
    @OperLog(operModul = "用户管理",operType = "删除",operDesc = "删除用户")
    public String deleteEmp(@PathVariable("id")int id){
        userService.delUser(id);
        return "redirect:/users";
    }

    //修改密码
    @GetMapping("/EditPassword")
    public String EditPassword(){
        return "redirect:/EditMyPassword.html";
    }

    //保存user与role的关联关系
    @OperLog(operModul = "用户角色关联管理",operType = "新增",operDesc = "新增用户角色关联关系")
    @RequestMapping(value = "/save_user_role",method = RequestMethod.POST)
    @ResponseBody
    @Log
    public Map<String,Object> add(int user_id,String role_name){
        int role_id = roleService.queryByRole_name(role_name);
        Sys_User_Role sys_user_role = new Sys_User_Role();
        sys_user_role.setRole_id(role_id);
        sys_user_role.setUser_id(user_id);
        userService.addUserRole(sys_user_role);
        Map<String,Object> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    @PostMapping("/find_user")
    //多条件查询用户信息
    public String find_user(@RequestParam(value="user",required =false) String user, @RequestParam(value="role",required =false) String role,
                            Model model){
        Sys_User sys_user = userService.find_user(user,role);
        model.addAttribute("users",sys_user);
        return "person";
    }

}
