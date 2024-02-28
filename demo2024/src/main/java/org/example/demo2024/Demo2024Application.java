package org.example.demo2024;

import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.extra.spring.EnableSpringUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
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
        ConfigurableApplicationContext cac = SpringApplication.run(Demo2024Application.class, args);
        ConfigurableEnvironment environment = cac.getEnvironment();
        String serverPort = environment.getProperty("server.port");
        String applicationName = environment.getProperty("spring.application.name");
        String sysProfilesName = environment.getProperty("spring.profiles.active");
        log.info("系统名:{}",applicationName);
        log.info("系统启动环境:{}",sysProfilesName);
        log.info("启动成功，API文档访问：http://127.0.0.1:{}/doc.html ,用户名:{},密码:{}",
                serverPort,
                SpringUtil.getProperty("knife4j.basic.username"),
                SpringUtil.getProperty("knife4j.basic.password"));
        log.info("系统监控访问：http://127.0.0.1:{}/javamelody ,用户名/密码:{}",
                serverPort,
                SpringUtil.getProperty("javamelody.init-parameters.authorized-sysUsers"));


    }

}
