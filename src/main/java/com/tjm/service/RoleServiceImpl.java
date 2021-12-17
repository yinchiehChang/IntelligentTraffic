package com.tjm.service;

import com.tjm.mapper.RoleMapper;
import com.tjm.pojo.Sys_Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Sys_Role> queryRoleList() {
        return roleMapper.queryRoleList();
    }

    @Override
    public int addRole(Sys_Role sys_role) {
        return roleMapper.addRole(sys_role);
    }

    @Override
    public List<String> queryRole_names() {
        return roleMapper.queryRole_names();
    }

    @Override
    public int findRoleByUserId(int user_id) {
        return roleMapper.findRoleByUserId(user_id);
    }

    @Override
    public String findRole_name(int user_id) {
        int role_id = roleMapper.findRoleByUserId(user_id);
        return roleMapper.findRole_name(role_id);
    }

    @Override
    public int queryByRole_name(String role_name) {
        return roleMapper.queryByRole_name(role_name);
    }

    @Override
    public Sys_Role findRole(String role_name) {
        return roleMapper.findRole(role_name);
    }

    @Override
    public String findRoleByUsername(String username) {
        return roleMapper.findRoleByUsername(username);
    }
}
