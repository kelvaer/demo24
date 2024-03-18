package org.example.demo2024;

import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.EnableSpringUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.example.demo2024.util.IpUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;

//EnableMethodCache，EnableCreateCacheAnnotation这两个注解分别激活Cached和CreateCache注解
@EnableMethodCache(basePackages = { "org.example.demo2024" })
@EnableCreateCacheAnnotation
//@EnableCaching
@EnableAsync
@MapperScan("org.example.demo2024.mapper")
@Slf4j
@EsMapperScan("org.example.demo2024.es.mapping")
@EnableFileStorage
@EnableSpringUtil
@SpringBootApplication(exclude = {Neo4jAutoConfiguration.class})
public class Demo2024Application {

    public static void main(String[] args){
        ConfigurableApplicationContext cac = SpringApplication.run(Demo2024Application.class, args);
        ConfigurableEnvironment environment = cac.getEnvironment();
        String serverPort = environment.getProperty("server.port");
        String applicationName = environment.getProperty("spring.application.name");
        String sysProfilesName = environment.getProperty("spring.profiles.active");
        log.info("系统名:{}",applicationName);
        log.info("系统启动环境:{}",sysProfilesName);
        String localServerIp = IpUtil.getLocalServerIp();
        if (StrUtil.isBlank(localServerIp)){
            localServerIp = "127.0.0.1";
        }
        log.info("启动成功，API文档访问：http://{}:{}/doc.html ,用户名:{},密码:{}",
                localServerIp,
                serverPort,
                SpringUtil.getProperty("knife4j.basic.username"),
                SpringUtil.getProperty("knife4j.basic.password"));
        log.info("系统监控访问：http://{}:{}/javamelody ,用户名/密码:{}",
                localServerIp,
                serverPort,
                SpringUtil.getProperty("javamelody.init-parameters.authorized-sysUsers"));
    }

}
