package com.tjm.config;
//全面拓展SpringMvc

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig  implements WebMvcConfigurer {
    //自定义视图控制
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/home.html").setViewName("home");
        registry.addViewController("/newbuilt.html").setViewName("newbuilt");
        registry.addViewController("/mywork.html").setViewName("mywork");
        registry.addViewController("/record.html").setViewName("record");
        registry.addViewController("/mytrain.html").setViewName("mytrain");
        registry.addViewController("/train.html").setViewName("train");
        registry.addViewController("/Points.html").setViewName("Points");
        registry.addViewController("/dossier_my.html").setViewName("dossier_my");
        registry.addViewController("/dossier_aq.html").setViewName("dossier_aq");
        registry.addViewController("/role.html").setViewName("role");
        registry.addViewController("/test_task.html").setViewName("test_task");
        registry.addViewController("/record.html").setViewName("record");
        registry.addViewController("/material_audit.html").setViewName("material_audit");
        registry.addViewController("/operrecord.html").setViewName("operrecord");
        registry.addViewController("/information.html").setViewName("information");
        registry.addViewController("/check_points.html").setViewName("check_points");
        registry.addViewController("/Points.html").setViewName("Points");
        registry.addViewController("/EditPoints.html").setViewName("EditPoints");
        registry.addViewController("/data_collection.html").setViewName("data_collection");
        registry.addViewController("/SaveRecord.html").setViewName("SaveRecord");
        registry.addViewController("/EditRecord.html").setViewName("EditRecord");
        registry.addViewController("/EditMyPassword.html").setViewName("EditMyPassword");
        registry.addViewController("/Editaudit.html").setViewName("Editaudit");
        registry.addViewController("/qualityIndexPage.html").setViewName("qualityIndexPage");
        registry.addViewController("/TestCases.html").setViewName("TestCases");
        registry.addViewController("/addCases.html").setViewName("addCases");
        registry.addViewController("/detail_records.html").setViewName("detail_records");
    }

    //自定义的国际化组件
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}


