package org.example.demo2024.neo4j.repo;

import org.example.demo2024.neo4j.meta.MovieEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author oppor
 */
@Repository
public interface MovieRepository extends Neo4jRepository<MovieEntity, Long> {
    //    @Query("MATCH (n:Movie) WHERE id(n) = $0 RETURN n") 这种方法是自己写Query语句进行查询
    List<MovieEntity> findMovieEntitiesById(Long id);
    MovieEntity findMovieEntityByTitle(String title);
}
