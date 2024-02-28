package org.example.demo2024.dto;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: demo2024
 * @description: test
 * @author: 作者名
 * @create: 2024/02/05
 */
@Data
@Configuration
public class Demo2 {
    private long id;
    private String name;

    @Bean(name="testDemo")
    public Demo2 generateDemo() {
        Demo2 demo = new Demo2();
        demo.setId(12345);
        demo.setName("test");
        return demo;
    }
}
