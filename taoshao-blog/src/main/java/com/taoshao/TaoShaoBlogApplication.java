package com.taoshao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author taoshao
 * @Date 2024/5/29
 */
@SpringBootApplication
@MapperScan("com.taoshao.mapper")
public class TaoShaoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaoShaoBlogApplication.class,args);
    }
}
