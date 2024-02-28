package org.example.demo2024;

import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("org.example.demo2024.mapper")
@Slf4j
@EsMapperScan("org.example.demo2024.es.mapping")
@EnableFileStorage
@EnableSpringUtil
@SpringBootApplication
public class Demo2024Application {

    public static void main(String[] args){
        SpringApplication.run(Demo2024Application.class, args);
        log.info("启动成功，API文档访问：http://127.0.0.1:8080/doc.html");
        log.info("系统监控访问：http://127.0.0.1:8080/javamelody");


    }

}