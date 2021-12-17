package com.tjm.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    //第三步：ShiroFilterFactoryBean
    @Bean(name="shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anon: 无需认证就可以访问
            authc: 必须认证了才能访问
            user: 必须拥有 记住我 功能才能访问
            perms: 拥有对某个资源的权限才能访问
            role: 拥有某个角色权限才能访问
         */

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("anyRoleFilter", new MyRolesAuthorizationFilter());
        bean.setFilters(filterMap);

        // LinkedHashMap 是有序的，进行顺序拦截器配置
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        //授权，授权要在拦截前面，正常情况下，未授权会跳转到未授权页面
//        filterChainMap.put("/users","anyRoleFilter[admin,system]");
//        filterChainMap.put("/roles","anyRoleFilter[admin,system]");
//        filterChainMap.put("/logs","anyRoleFilter[admin,audit]");
        //拦截，

        bean.setFilterChainDefinitionMap(filterChainMap);

        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        //设置未授权请求
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }

    //第二步：DefaultWebSecurityManager
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //第一步：创建realm对象，需要自定义类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
