package com.tjm.controller;

import com.tjm.config.Log;
import com.tjm.config.OperLog;
import com.tjm.pojo.*;
import com.tjm.service.MenuService;
import com.tjm.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
   private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 通过当前用户找出该用户对应角色的列表权限
     * @return 列表list结果
     */
    @GetMapping("/listMenu")
    @ResponseBody
    public List<Menu> listMenu() {
        List<Menu> result;
        Subject subject = SecurityUtils.getSubject();
        Sys_User currentUser = (Sys_User) subject.getPrincipal();//拿到user对象
        int role_id = roleService.findRoleByUserId(currentUser.getUser_id());
        result = menuService.listMenuByRole(role_id);
        return result;
    }

    /**
     * 找出所有列表
     * @return
     */
    @RequestMapping(value = "/findMenuList",method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> findMenuList(){
        List<Menu> menus = menuService.findMenuList();
        return menus;
    }

    //添加角色与目录关联关系
    @OperLog(operModul = "角色目录关联管理",operType = "新增",operDesc = "新增角色目录关联关系")
    @RequestMapping(value = "/save_role_menu",method = RequestMethod.POST)
    @ResponseBody
    @Log
    public void add(String role_name,String menu_names,String menu_ids){
       int role_id = roleService.queryByRole_name(role_name);
       String[] menuIds = menu_ids.split("、");
        for (int i = 0; i <menuIds.length; i++) {
            int menu_id = Integer.parseInt(menuIds[i]);
            Sys_Role_Menu sys_role_menu = new Sys_Role_Menu();
            sys_role_menu.setMenu_id(menu_id);
            sys_role_menu.setRole_id(role_id);
            menuService.insert_role_menu(sys_role_menu);
        }
//        Map<String,String> res = new HashMap<>();
//        res.put("code","1");
//        res.put("message","新增成功");
//        return res;
    }
}
