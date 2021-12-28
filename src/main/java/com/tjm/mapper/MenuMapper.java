package com.tjm.mapper;

import com.tjm.pojo.Menu;
import com.tjm.pojo.Sys_Role_Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MenuMapper {

    //查询指定id对应的菜单
    List<Menu> listMenuByRole(int role_id);

    //查询所有菜单
    public List<Menu> findMenuList();

    //插入Sys_Role_Menu
    int insert_role_menu(Sys_Role_Menu sys_role_menu);

    int getMaxMenuId();

    int insertSysMenu(Menu menu);

    int deleteSysMenu(String name);
}
