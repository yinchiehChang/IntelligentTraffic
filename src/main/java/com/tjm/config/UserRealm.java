package com.tjm.config;

import com.tjm.mapper.RoleMapper;
import com.tjm.pojo.Sys_User;
import com.tjm.service.RoleService;
import com.tjm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;

    //授权,真正授权配置的页面
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        Sys_User currentUser = (Sys_User) subject.getPrincipal();//拿到user对象

        //查询当前角色名
        String role = roleService.findRole_name(currentUser.getUser_id());
        info.addRole(role);

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //连接真实数据库
        Sys_User user = userService.queryUserByName(userToken.getUsername());
        if(user == null){//没有这个人
            return null;
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
