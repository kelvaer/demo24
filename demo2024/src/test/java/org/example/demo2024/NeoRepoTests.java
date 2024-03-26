package org.example.demo2024;

import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.neo4j.repo.MovieRepository;
import org.example.demo2024.neo4j.repo.PersonRepository;
import org.example.demo2024.nlp.MainPartExtractor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.demo2024.neo4j.meta.MovieEntity;
import org.example.demo2024.neo4j.meta.PersonEntity;
import org.example.demo2024.neo4j.meta.Roles;

import java.util.Collections;


@SpringBootTest
@Slf4j
class NeoRepoTests {
    @Test
    void test1211212(){
        MainPartExtractor.mpTest();
    }

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void test351531(){
        // 创建节点
        MovieEntity movie = new MovieEntity("你的名字","影片讲述了男女高中生在梦中相遇，并寻找彼此的故事。");
        Roles roles1 = new Roles(new PersonEntity(1998,"上白石萌音"), Collections.singletonList("宫水三叶"));
        Roles roles2 = new Roles(new PersonEntity(1993,"神木隆之介"), Collections.singletonList("立花泷"));
        PersonEntity director = new PersonEntity(1973,"新海诚");
// 添加关系
        movie.getActorsAndRoles().add(roles1);
        movie.getActorsAndRoles().add(roles2);
        movie.getDirectors().add(director);
// 存入图数据库持久化
        movieRepository.save(movie);

    }


    @Test
    void test6565(){
        // 查询
        // 查询
        PersonEntity person = personRepository.findPersonEntityByName("上白石萌音");
        System.out.println("查询名字为“上白石萌音”的PersonEntity："+person);
        MovieEntity movieQueried = movieRepository.findMovieEntityByTitle("你的名字");
        System.out.println("查询名字为“你的名字”的MovieEntity："+movieQueried);

    }

    @Test
    void testUpdate1513(){
        PersonEntity person = personRepository.findPersonEntityByName("上白石萌音");
        // 更新(更新主要是三步：1.获取实体id；2.修改实体属性；3.更新实体）
        // 注意：repository的save方法【对应的实体若id一致】则为修改，否则为新建。
        Long personId = person.getId();
        person.setBorn(1997);
        personRepository.save(person);

        person = personRepository.findPersonEntityByName("上白石萌音");
        System.out.println(personId == person.getId()?"\n更新“上白石萌音”出生日期为1997信息成功！：\n"+person:"更新信息失败！");

    }


    @Test
    void testDel1135123(){
        // 删除所有节点和关系
        movieRepository.deleteAll();
        personRepository.deleteAll();
    }


}
