package org.example.demo2024.neo4j;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @program: demo2024
 * @description: cfg
 * @author: 作者名
 * @create: 2024/02/20
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class Neo4jConfig {
    //neo4j://localhost:7687
//    private static final String URI = "http://localhost:7474";
//    private static final String URI = "neo4j://localhost:7687";
//    private static final String USER = "test";
//    private static final String PASSWORD = "12345678";

    @Value("${my.neo4j.uri}")
    private String url;

    @Value("${my.neo4j.username}")
    private String username;

    @Value("${my.neo4j.password}")
    private String password;

    @Value("${my.neo4j.database}")
    private String database;


    @Bean
    public  Session openSession() {
        log.info("---------------neo4j-----start!!!---------------------");

        Configuration configuration = new Configuration.Builder()
                .uri(url)
                .database(database)
                .credentials(username, password)
                .withBasePackages("org.example.demo2024.neo4j")
                .connectionPoolSize(10)
                .connectionLivenessCheckTimeout(30)
                .verifyConnection(true)
                .build();

        log.info("neo4j cfg:{}",configuration.getURI());

        return new SessionFactory(configuration).openSession();

    }
}
