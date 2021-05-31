package com.sxzhyl.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.sxzhyl.jwt.mapper")
public class JwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class);
    }
}