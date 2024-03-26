package org.example.demo2024;

import lombok.extern.slf4j.Slf4j;
//import org.example.demo2024.neo4j.Neo4jConfig;
//import org.example.demo2024.neo4j.meta.NeoNode;
////import org.example.demo2024.neo4j.repo.NeoNodeRepo;
//import org.example.demo2024.neo4j.meta.NeoRelation;
//import org.neo4j.ogm.session.Session;
//import org.springframework.beans.factory.annotation.Autowired;
import org.checkerframework.checker.units.qual.A;
import org.example.demo2024.neo4j.meta.MovieEntity;
import org.example.demo2024.neo4j.meta.PersonEntity;
import org.example.demo2024.neo4j.meta.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.query.QueryFragmentsAndParameters;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.neo4j.ogm.session.Session;

/**
 * @program: demo2024
 * @description: neo4j
 * @author: 作者名
 * @create: 2024/02/19
 */
@SpringBootTest
@Slf4j
class NeoTest {

    @Autowired
    private Neo4jTemplate neo4jTemplate;
    @Test
    void test12313(){
        Optional<PersonEntity> person;
        Map<String,Object> map = new HashMap<>();
        map.put("name","新海诚");
        person = neo4jTemplate.findOne("MATCH (n:Person {name: $name}) RETURN n",map, PersonEntity.class);
        System.out.println("新海诚 的Person节点：\n"+person);
    }
    @Test
    void test2222(){

        // 查询（不太推荐用Neo4jTemplate进行过滤查询，因为需要手动写cypherQuery,需要开发人员了解一下cypherQuery的写法）
        Optional<PersonEntity> person;
        // 1. 通过id查询
        person = neo4jTemplate.findById(12, PersonEntity.class);
        System.out.println("id为12号的Person节点：\n"+person);

        // 2. 通过属性查询节点，如name 需要手写cypherQuery语句
        Map<String,Object> map = new HashMap<>();
        map.put("name","新海诚");
        // 两种写法都对，看个人喜好 n是一个变量随意取，{}或者where填写query的filter过滤条件
        person = neo4jTemplate.findOne("MATCH (n:Person {name: $name}) RETURN n",map, PersonEntity.class);
//        person = neo4jTemplate.findOne("MATCH (n:Person) WHERE n.name = $name RETURN n",map, PersonEntity.class);
        System.out.println("\n查询名字为新海诚的Person节点:\n"+person);

        // 3. 通过属性关系查询节点
        map = new HashMap<>();
        map.put("roles",Collections.singletonList("宫水三叶"));
        // 方法1.使用toExecutableQuery查询
        QueryFragmentsAndParameters parameters = new QueryFragmentsAndParameters(
                "MATCH (person:Person) -[ relation:ACTED_IN]-> (movie:Movie) \n" +
                        "WHERE relation.roles = $roles\n" +
                        "RETURN person",map);
        List<PersonEntity> roles = neo4jTemplate.toExecutableQuery(PersonEntity.class, parameters).getResults();
        // 方法2.使用findOne查询
//        Optional<PersonEntity> roles = neo4jTemplate.findOne(
//                "MATCH (person:Person) -[ relation:ACTED_IN]-> (movie:Movie) \n" +
//                "WHERE relation.roles = $roles\n" +
//                "RETURN person",map,PersonEntity.class);

        System.out.println("\n查询角色为“宫水三叶”的演员：\n"+roles);


    }
    @Test
    void test32401(){
        // 创建节点实体

        MovieEntity movie = new MovieEntity("你的名字","影片讲述了男女高中生在梦中相遇，并寻找彼此的故事。");// 电影实体节点

// 定义（参演）关系
// new Roles 参数1：Person实体，演员的出生年和姓名；参数2：演员名字列表（考虑到一个演员可能参演多个角色）
// 参数1是目标关系实体节点 参数2是关系属性
        Roles roles1 = new Roles(new PersonEntity(1998,"上白石萌音"), Collections.singletonList("宫水三叶"));
        Roles roles2 = new Roles(new PersonEntity(1993,"神木隆之介"), Collections.singletonList("立花泷"));
        PersonEntity director = new PersonEntity(1973,"新海诚");

// 添加movie的演员实体，加入（参演）关系
        movie.getActorsAndRoles().add(roles1);
        movie.getActorsAndRoles().add(roles2);
        movie.getDirectors().add(director);

// 存入图数据库持久化
        neo4jTemplate.save(movie);

    }


    @Test
    void testUpdateByTemplate(){
        Optional<PersonEntity> person;
        Map<String,Object> map = new HashMap<>();
        map.put("name","新海诚");
        person = neo4jTemplate.findOne("MATCH (n:Person {name: $name}) RETURN n",map, PersonEntity.class);
        Long userId = person.get().getId();// 记录当前查询的"新海诚"的节点id
        // 更新①---------更新“新海诚”的name为曾用名“新津诚”（这是他的曾用名）
        map.put("name","新海诚");
        map.put("usedName","新津诚");
        QueryFragmentsAndParameters queryFragmentsAndParameters =
                new QueryFragmentsAndParameters(
                        "MATCH (n:Person{name: $name}) SET n.name = $usedName",
                        map);
        neo4jTemplate.toExecutableQuery(
                PersonEntity.class,
                queryFragmentsAndParameters).getResults();
        Optional<PersonEntity> person1 = neo4jTemplate.findById(userId, PersonEntity.class);
        System.out.println("\n更新“新海诚”的name为曾用名“新津诚”（这是他的曾用名）:\n"+person1);
        // 更新②---------更新“新津诚”的name为“新海诚”
        person.get().setName("新海诚");
        neo4jTemplate.save(person.get());
        Optional<PersonEntity> person2 = neo4jTemplate.findById(userId, PersonEntity.class);
        System.out.println("\n更新“新津诚”的name为“新海诚”:\n"+person2);

    }


    @Test
    void testDel12131(){
        // 删除所有节点和关系（删除节点会响应删除关联关系）[也可以用cypherQuery执行，不再赘述]
        neo4jTemplate.deleteAll(MovieEntity.class);
        neo4jTemplate.deleteAll(PersonEntity.class);
    }


    @Test
    void testDel(){
        // 删除所有节点和关系（删除节点会响应删除关联关系）[也可以用cypherQuery执行，不再赘述]
        neo4jTemplate.deleteAll(MovieEntity.class);
        neo4jTemplate.deleteAll(PersonEntity.class);


        // 创建节点实体
        MovieEntity movie = new MovieEntity("你的名字","影片讲述了男女高中生在梦中相遇，并寻找彼此的故事。");

        // new Roles 参数1：Person实体，演员的出生年和姓名；参数2：演员名字列表（考虑到一个演员可能参演多个角色）
        // 参数1是目标关系实体节点 参数2是关系属性
        Roles roles1 = new Roles(new PersonEntity(1998,"上白石萌音"), Collections.singletonList("宫水三叶"));
        Roles roles2 = new Roles(new PersonEntity(1993,"神木隆之介"), Collections.singletonList("立花泷"));
        PersonEntity director = new PersonEntity(1973,"新海诚");
        // 添加movie的演员实体，加入（参演）关系
        movie.getActorsAndRoles().add(roles1);
        movie.getActorsAndRoles().add(roles2);
        movie.getDirectors().add(director);

        // 存入图数据库持久化
        neo4jTemplate.save(movie);

        // 查询（不太推荐用Neo4jTemplate进行过滤查询，因为需要手动写cypherQuery,需要开发人员了解一下cypherQuery的写法）
        Optional<PersonEntity> person;
        // 1. 通过id查询
        person = neo4jTemplate.findById(12, PersonEntity.class);
        System.out.println("id为12号的Person节点：\n"+person);

        // 2. 通过属性查询节点，如name 需要手写cypherQuery语句
        Map<String,Object> map = new HashMap<>();
        map.put("name","新海诚");
        // 两种写法都对，看个人喜好 n是一个变量随意取，{}或者where填写query的filter过滤条件
        person = neo4jTemplate.findOne("MATCH (n:Person {name: $name}) RETURN n",map, PersonEntity.class);
//        person = neo4jTemplate.findOne("MATCH (n:Person) WHERE n.name = $name RETURN n",map, PersonEntity.class);
        System.out.println("\n查询名字为新海诚的Person节点:\n"+person);

        // 3. 通过属性关系查询节点
        map = new HashMap<>();
        map.put("roles",Collections.singletonList("宫水三叶"));
        // 方法1.使用toExecutableQuery查询
        QueryFragmentsAndParameters parameters = new QueryFragmentsAndParameters(
                "MATCH (person:Person) -[ relation:ACTED_IN]-> (movie:Movie) \n" +
                        "WHERE relation.roles = $roles\n" +
                        "RETURN person",map);
        List<PersonEntity> roles = neo4jTemplate.toExecutableQuery(PersonEntity.class, parameters).getResults();
        // 方法2.使用findOne查询
//        Optional<PersonEntity> roles = neo4jTemplate.findOne(
//                "MATCH (person:Person) -[ relation:ACTED_IN]-> (movie:Movie) \n" +
//                "WHERE relation.roles = $roles\n" +
//                "RETURN person",map,PersonEntity.class);
        System.out.println("\n查询角色为“宫水三叶”的演员：\n"+roles);

        Long userId = person.get().getId();// 记录当前查询的"新海诚"的节点id
        // 更新①---------更新“新海诚”的name为曾用名“新津诚”（这是他的曾用名）
        map.put("name","新海诚");
        map.put("usedName","新津诚");
        QueryFragmentsAndParameters queryFragmentsAndParameters =
                new QueryFragmentsAndParameters(
                        "MATCH (n:Person{name: $name}) SET n.name = $usedName",
                        map);
        neo4jTemplate.toExecutableQuery(
                PersonEntity.class,
                queryFragmentsAndParameters).getResults();
        Optional<PersonEntity> person1 = neo4jTemplate.findById(userId, PersonEntity.class);
        System.out.println("\n更新“新海诚”的name为曾用名“新津诚”（这是他的曾用名）:\n"+person1);
        // 更新②---------更新“新津诚”的name为“新海诚”
        person.get().setName("新海诚");
        neo4jTemplate.save(person.get());
        Optional<PersonEntity> person2 = neo4jTemplate.findById(userId, PersonEntity.class);
        System.out.println("\n更新“新津诚”的name为“新海诚”:\n"+person2);
//————————————————
//
//        版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。
//
//        原文链接：https://blog.csdn.net/qq_55368677/article/details/127132277
    }



//
//
//    @Autowired
//    private Session session;
//
//    @Test
//    void testSelectRel(){
//        Collection<NeoNode> people = session.loadAll(NeoNode.class);
//        for (NeoNode person : people) {
//            log.info("p:{}", person.toString());
//        }
//
//        Collection<NeoRelation> neoRelations = session.loadAll(NeoRelation.class);
//        for (NeoRelation r : neoRelations) {
//            log.info("r:{}", r.toString());
//        }
//
//    }
//
//    @Test
//    void testSelectNode() {
//
//
//
//        NeoNode load1 = session.load(NeoNode.class, 1206L);
//        System.err.println(load1);
//        NeoNode load2 = session.load(NeoNode.class, 1207L);
//        System.err.println(load2);
//
//        NeoRelation r1 = new NeoRelation();
//        r1.setStartNode(load1);
//        r1.setEndNode(load2);
//        r1.setRelation("一毛钱关系");
//        session.save(r1);
//    }
//
//    //
//    @Test
//    void testSaveNode() {
//
//
//        NeoNode person = new NeoNode();
//        person.setName("apple");
//        person.setKind("phone");
//        person.setWeight(70L);
//        session.save(person);
//
//        session.clear();
//
//        NeoNode person2 = new NeoNode();
//        person2.setName("camera");
//        person2.setKind("pro");
//        person2.setWeight(10L);
//        session.save(person2);
//
//
//    }
//
}
