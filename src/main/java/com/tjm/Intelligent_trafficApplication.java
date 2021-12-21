package com.tjm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@MapperScan("com.tjm.mapper")
@SpringBootApplication
public class Intelligent_trafficApplication {

    public static void main(String[] args) {

        SpringApplication.run(Intelligent_trafficApplication.class, args);
    }

}
