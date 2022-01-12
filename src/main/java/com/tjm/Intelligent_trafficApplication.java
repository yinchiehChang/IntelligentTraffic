package com.tjm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@MapperScan("com.tjm.mapper")
@SpringBootApplication
public class Intelligent_trafficApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Intelligent_trafficApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Intelligent_trafficApplication.class, args);
    }

}
