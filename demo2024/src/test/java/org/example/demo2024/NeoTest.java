package org.example.demo2024;

import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.neo4j.Neo4jConfig;
import org.example.demo2024.neo4j.meta.NeoNode;
//import org.example.demo2024.neo4j.repo.NeoNodeRepo;
import org.example.demo2024.neo4j.meta.NeoRelation;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import org.neo4j.ogm.session.Session;

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
    private Session session;

    @Test
    void testSelectRel(){
        Collection<NeoNode> people = session.loadAll(NeoNode.class);
        for (NeoNode person : people) {
            log.info("p:{}", person.toString());
        }

        Collection<NeoRelation> neoRelations = session.loadAll(NeoRelation.class);
        for (NeoRelation r : neoRelations) {
            log.info("r:{}", r.toString());
        }

    }

    @Test
    void testSelectNode() {



        NeoNode load1 = session.load(NeoNode.class, 1206L);
        System.err.println(load1);
        NeoNode load2 = session.load(NeoNode.class, 1207L);
        System.err.println(load2);

        NeoRelation r1 = new NeoRelation();
        r1.setStartNode(load1);
        r1.setEndNode(load2);
        r1.setRelation("一毛钱关系");
        session.save(r1);
    }

    //
    @Test
    void testSaveNode() {


        NeoNode person = new NeoNode();
        person.setName("apple");
        person.setKind("phone");
        person.setWeight(70L);
        session.save(person);

        session.clear();

        NeoNode person2 = new NeoNode();
        person2.setName("camera");
        person2.setKind("pro");
        person2.setWeight(10L);
        session.save(person2);


    }

}
