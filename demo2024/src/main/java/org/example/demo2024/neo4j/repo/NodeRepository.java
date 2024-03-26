//package org.example.demo2024.neo4j.repo;
//
//import org.example.demo2024.neo4j.meta.NeoNode;
//import org.springframework.data.neo4j.repository.query.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface NodeRepository extends Neo4jRepository<NeoNode,Long> {
//    @Query("MATCH p=(n:Person) RETURN p")
//    List<NeoNode> selectAll();
//
//    @Query("MATCH(p:Person{name:{name}}) return p")
//    NeoNode findByName(String name);
//}
