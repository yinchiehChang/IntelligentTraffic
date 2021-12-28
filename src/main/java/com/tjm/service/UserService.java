package com.tjm.service;

import com.tjm.pojo.Android_User;
import com.tjm.pojo.Sys_User;
import com.tjm.pojo.Sys_User_Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public Sys_User queryUserByName(String username);

    public List<Sys_User> queryUserList();

    int addUser(Sys_User user);

    int delUser(int id);

    String queryRole(int id);

    int addUserRole(Sys_User_Role sys_user_role);

    int updatePass(int user_id,String newpass);

    //根据用户名和角色查找对应的角色信息
    Sys_User find_user(@Param("user")String user, @Param("role")String role);

    //查询所有安卓用户
    List<Android_User> findAndroid_Users();
}
