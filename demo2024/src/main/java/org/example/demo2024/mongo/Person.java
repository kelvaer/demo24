package org.example.demo2024.mongo;

import com.anwen.mongo.annotation.ID;
import com.anwen.mongo.annotation.collection.CollectionField;
import com.anwen.mongo.annotation.collection.CollectionName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: demo2024
 * @description: person文档
 * @author: 作者名
 * @create: 2024/02/04
 */
@Data
@CollectionName("tb_person")
public class Person {
    @ID     //使用ID注解，标注此字段为MongoDB的_id，或者继承BaseModelID类
    private String id;
    @CollectionField("person_name")
    private String name;
    private int age;
    private String address;

    private LocalDateTime createTime;
}
