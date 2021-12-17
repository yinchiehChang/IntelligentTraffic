package com.tjm.mapper;

import com.tjm.pojo.Android_User;
import com.tjm.pojo.Sys_User;
import com.tjm.pojo.Sys_User_Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    //通过姓名查询用户
    public Sys_User queryUserByName(String name);

    //查询全部用户信息
    List<Sys_User> queryUserList();

    //添加一个用户
    int addUser(Sys_User user);

    //删除一个用户
    int delUser(@Param("id") int id);

    //根据用户id查找用户角色
    String queryRole(@Param("id") int id);

    //插入用户角色关联关系表
    int addUserRole(Sys_User_Role sys_user_role);

    //根据用户名和角色查找对应的角色信息
    Sys_User find_user(String user,String role);

    //查询所有安卓用户
    List<Android_User> findAndroid_Users();
}


