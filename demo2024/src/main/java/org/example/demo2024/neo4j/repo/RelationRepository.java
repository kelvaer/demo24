//package org.example.demo2024.neo4j.repo;
//
//import org.example.demo2024.neo4j.meta.NeoNode;
//import org.example.demo2024.neo4j.meta.NeoRelation;
//import org.springframework.data.neo4j.repository.query.Query;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface RelationRepository extends Neo4jRepository<NeoRelation,Long> {
//    @Query("MATCH p=(n:Person)-[r:Relation]->(m:Person) " +
//            "WHERE id(n)={startNode} and id(m)={endNode} and r.relation={relation}" +
//            "RETURN p")
//    List<NeoRelation> findRelation(@Param("startNode") NeoNode startNode,
//                                @Param("endNode") NeoNode endNode,
//                                @Param("relation") String relation);
//}
