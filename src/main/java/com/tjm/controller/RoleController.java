package com.tjm.controller;

import com.tjm.config.Log;
import com.tjm.config.OperLog;
import com.tjm.pojo.Sys_Role;
import com.tjm.pojo.Sys_Role_Menu;
import com.tjm.service.MenuService;
import com.tjm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/addRole")
    public String toAddpage(){
        return "addRole";
    }

    //查询所有角色
    @RequestMapping("/roles")
    @Log
    @OperLog(operModul = "角色管理",operType = "查询",operDesc = "查询所有角色")
    public String list(Model model){
        Collection<Sys_Role> roles = roleService.queryRoleList();
        //将结果放在请求中
        model.addAttribute("roles",roles);
        return "role";
    }

    //添加角色
    @OperLog(operModul = "角色管理",operType = "新增",operDesc = "新增角色")
    @RequestMapping(value = "/role_add",method = RequestMethod.POST)
    @ResponseBody
    @Log
    public Map<String, Object> add(Sys_Role sys_role,String menu_names,String menu_ids){
        Map<String, Object> res = new HashMap<>();
        try{
            roleService.addRole(sys_role);
        }catch (Exception e){
            String errormsg = "添加角色信息失败！！";
            System.out.println(errormsg);
            res.put("errormsg", errormsg);
            e.printStackTrace();
        }
        String role_name = sys_role.getRole_name();
        try{
            int role_id = roleService.queryByRole_name(role_name);
            String[] menuIds = menu_ids.split("、");
            for (int i = 0; i <menuIds.length; i++) {
                int menu_id = Integer.parseInt(menuIds[i]);
                Sys_Role_Menu sys_role_menu = new Sys_Role_Menu();
                sys_role_menu.setMenu_id(menu_id);
                sys_role_menu.setRole_id(role_id);
                menuService.insert_role_menu(sys_role_menu);
            }
            res.put("code", "1");
            res.put("msg","添加角色成功！");
        }catch (Exception e){
            String errormsg = "添加角色目录关联信息失败！！";
            System.out.println(errormsg);
            res.put("errormsg", errormsg);
            e.printStackTrace();
        }

        return res;
    }


    @PostMapping("/find_role")
    //多条件查询角色信息
    public String find_role(@RequestParam(value="role_name",required =false) String role_name,
                            Model model){
        Sys_Role sys_role = roleService.findRole(role_name);
        model.addAttribute("roles",sys_role);
        return "role";
    }

}
