package com.tjm.service;


import com.tjm.pojo.Menu;
import com.tjm.pojo.Sys_Role_Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {

    public List<Menu> listMenuByRole(int role_id);

    public List<Menu> findMenuList();

    public int insert_role_menu(Sys_Role_Menu sys_role_menu);
}
