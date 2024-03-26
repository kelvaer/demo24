package org.example.demo2024;

import cn.hutool.core.collection.ListUtil;
import edu.stanford.nlp.trees.TreeGraphNode;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.neo4j.meta.One2OneRelBean;
import org.example.demo2024.neo4j.meta.PersonEntity;
import org.example.demo2024.neo4j.repo.PersonRepository;
import org.example.demo2024.nlp.MainPart;
import org.example.demo2024.nlp.MainPartExtractor;
import org.example.demo2024.util.GraphUtil;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.QueryRunner;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @program: demo2024
 * @description: test 325
 * @author: 作者名
 * @create: 2024/03/25
 */
@SpringBootTest
@Slf4j
class Test325 {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void t1(){

        PersonEntity save = personRepository.save(PersonEntity.builder()
                        .born(1999)
                        .title("t3")
                        .name("p3")
                .build());

        System.err.println(save.toString());
    }

    @Test
    void t2(){
        List<PersonEntity> all = personRepository.findAll();
        System.err.println(all);
    }

    @Test
    void t3(){
        PersonEntity t1 = personRepository.findPersonEntityByName("p1");
        PersonEntity t2 = personRepository.findPersonEntityByName("p2");

        One2OneRelBean one2OneRelBean = new One2OneRelBean();
        one2OneRelBean.setValue("男朋友");
        one2OneRelBean.setPersonEntity(t2);

        PersonEntity t3 = personRepository.findPersonEntityByName("p3");
        One2OneRelBean r2 = new One2OneRelBean();
        r2.setValue("女朋友");
        r2.setPersonEntity(t3);

        t1.setOne2OneRelBeanList(ListUtil.toList(one2OneRelBean,r2));

        personRepository.save(t1);

    }





    @Test
    void testwqw21e(){
//        String sentence = "海拉又被称为死亡女神";
//        String sentence = "死亡女神捏碎了雷神之锤";
        String sentence = "雷神之锤属于索尔";
        MainPart mp = MainPartExtractor.getMainPart(sentence);

        TreeGraphNode subject = mp.getSubject();    //主语
        TreeGraphNode predicate = mp.getPredicate();//谓语
        TreeGraphNode object = mp.getObject();      //宾语

        if (Objects.isNull(subject) || Objects.isNull(object)){

            return;
        }else {
            PersonEntity startNode = addNode(subject);
            PersonEntity endNode = addNode(object);
            String relationName = GraphUtil.getNodeValue(predicate);//关系词

            One2OneRelBean r2 = new One2OneRelBean();
            r2.setValue(relationName);
            r2.setPersonEntity(endNode);

            startNode.setOne2OneRelBeanList(ListUtil.toList(r2));
            personRepository.save(startNode);

        }
    }

    private PersonEntity addNode(TreeGraphNode treeGraphNode){

        String nodeName = GraphUtil.getNodeValue(treeGraphNode);

        PersonEntity existNode = personRepository.findPersonEntityByName(nodeName);
        if (Objects.nonNull(existNode))
            return existNode;

        PersonEntity node =new PersonEntity();
        node.setName(nodeName);
        return personRepository.save(node);
    }


}
