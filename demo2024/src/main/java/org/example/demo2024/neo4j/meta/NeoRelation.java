//package org.example.demo2024.neo4j.meta;
//
///**
// * @program: demo2024
// * @description: 关联关系
// * @author: 作者名
// * @create: 2024/02/19
// */
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.neo4j.ogm.annotation.*;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@RelationshipEntity(type = "Relation") //定义1个关系对象，对象的标签为NeoRelation
//public class NeoRelation {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//    @StartNode  //关系起始节点
//    private NeoNode startNode;
//    @EndNode    //关系终止节点
//    private NeoNode endNode;
//    @Property     //定义具体的关系
//    private String relation;
//
//
//
//}
//
//
