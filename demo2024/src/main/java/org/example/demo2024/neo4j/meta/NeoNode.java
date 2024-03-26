//package org.example.demo2024.neo4j.meta;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.neo4j.ogm.annotation.GeneratedValue;
//import org.neo4j.ogm.annotation.Id;
//import org.neo4j.ogm.annotation.NodeEntity;
//import org.neo4j.ogm.annotation.Property;
//
///**
// * @program: demo2024
// * @description: 节点
// * @author: 作者名
// * @create: 2024/02/19
// */
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@NodeEntity(label = "Person") //定义1个节点对象，对象的标签为NeoNode
//public class NeoNode {
//
//    @Id
//    @GeneratedValue  //2个注解加一起才能自动生成图元素的id，默认生成的id是从0开始自增的
//    private Long id;
//
//    @Property(name = "name")
//    private String name;
//
//    @Property(name = "title")
//    private String title;
//}
