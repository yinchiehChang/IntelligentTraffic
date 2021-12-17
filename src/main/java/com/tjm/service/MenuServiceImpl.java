package com.tjm.service;

import com.tjm.mapper.MenuMapper;
import com.tjm.pojo.Menu;
import com.tjm.pojo.Sys_Role_Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> listMenuByRole(int role_id) {
        List<Menu> menuList;
        List<Menu> newMenuList = new ArrayList<>();
        try {
            //1、根据角色获得所有的菜单（包括一级和二级）
            menuList = menuMapper.listMenuByRole(role_id);
            System.out.println(menuList);
            for (int i = 0; i < menuList.size(); i++) {
                Menu menu = menuList.get(i);
                List<Menu> childMenuList = new ArrayList<>();
                //2、拼装二级菜单
                if (menu.getPid() == 0) {
                    for (int j = 0; j < menuList.size(); j++) {
                        if (Objects.equals(menu.getMenu_id(), menuList.get(j).getPid())) {
                            childMenuList.add(menuList.get(j));
                        }
                    }
                    menu.setChildMenu(childMenuList);
                    newMenuList.add(menu);
                }
            }
        } catch (Exception e) {
            System.out.println("错误！");
        }
        return newMenuList;
    }

    @Override
    public List<Menu> findMenuList() {
        return menuMapper.findMenuList();
    }

    @Override
    public int insert_role_menu(Sys_Role_Menu sys_role_menu) {
        return menuMapper.insert_role_menu(sys_role_menu);
    }
}
