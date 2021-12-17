package com.tjm.mapper;

import com.tjm.pojo.Sys_Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleMapper {
    //查询全部角色信息
    List<Sys_Role> queryRoleList();

    //通过username查找用户角色信息
    String findRoleByUsername(@Param("username") String username);

    //添加一个角色
    int addRole(Sys_Role sys_role);

    //查找所有的角色名
    List<String> queryRole_names();

    //通过user_id查找用户角色id
    int findRoleByUserId(@Param("user_id") int user_id);

    //通过user_id查找用户角色信息
    String findRole_name(@Param("role_id") int role_id);


    //通过role_name查找对应角色id
    int queryByRole_name(@Param("role_name") String role_name);

    //通过role_name查找对应role
    Sys_Role findRole(@Param("role_name") String role_name);
}
