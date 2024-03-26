package org.example.demo2024.neo4j.repo;


import org.example.demo2024.neo4j.meta.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author oppor
 */
@Repository
public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {

    PersonEntity findPersonEntityByName(String name);


    //MATCH p=()-[r:Friends]->() where r.value='男朋友'  RETURN p



}
