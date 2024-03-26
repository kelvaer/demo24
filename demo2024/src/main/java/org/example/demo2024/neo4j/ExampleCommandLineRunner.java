//package org.example.demo2024.neo4j;
//
//import org.neo4j.driver.Driver;
//import org.neo4j.driver.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author oppor
// */
//@Component
//@Slf4j
//public class ExampleCommandLineRunner implements CommandLineRunner {
//
//    @Resource
//    private  Driver driver;
//    @Resource
//    private  ConfigurableApplicationContext applicationContext;
//    @Resource
//    public  Session session;
//
//    @Bean
//    Session session(){
//        return session;
//    }
//
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//    }
//}
