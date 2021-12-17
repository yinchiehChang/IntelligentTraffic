package com.tjm.service;

import com.tjm.mapper.UserMapper;
import com.tjm.pojo.Android_User;
import com.tjm.pojo.Sys_User;
import com.tjm.pojo.Sys_User_Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public Sys_User queryUserByName(String username) {
        return userMapper.queryUserByName(username);
    }

    @Override
    public List<Sys_User> queryUserList() {
        return userMapper.queryUserList();
    }

    @Override
    public int addUser(Sys_User user){
        return userMapper.addUser(user);
    }

    @Override
    public int delUser(int id){
        return userMapper.delUser(id);
    }

    @Override
    public String queryRole(int id){
        return userMapper.queryRole(id);
    }

    @Override
    public int addUserRole(Sys_User_Role sys_user_role) {
        return userMapper.addUserRole(sys_user_role);
    }

    @Override
    public Sys_User find_user(String user, String role) {
        return userMapper.find_user(user,role);
    }

    @Override
    public List<Android_User> findAndroid_Users() {
        return userMapper.findAndroid_Users();
    }

}
