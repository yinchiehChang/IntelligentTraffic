package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    int menu_id;
    int pid;
    String menu_name;
    String url;
    String icon;

    /**
     * 子菜单Menu列表
     */
   List<Menu> childMenu;
}
