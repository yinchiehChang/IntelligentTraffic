package com.tjm.service;

import com.tjm.pojo.Sys_Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    public List<Sys_Role> queryRoleList();

    //通过username查找用户角色信息
    String findRoleByUsername(@Param("username") String username);

    int addRole(Sys_Role sys_role);

    //查找所有角色
    List<String> queryRole_names();

    //通过user_id查找用户角色id
    int findRoleByUserId(@Param("user_id") int user_id);

    //通过user_id查询用户角色名
    String findRole_name(@Param("user_id") int user_id);

    //通过role_name查找对应角色id
    int queryByRole_name(@Param("role_name") String role_name);

    //通过role_name查找对应role
    Sys_Role findRole(@Param("role_name") String role_name);
}
