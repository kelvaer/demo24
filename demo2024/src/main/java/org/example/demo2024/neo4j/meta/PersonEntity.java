package org.example.demo2024.neo4j.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;


/**
 * @author oppor
 * https://spring.io/guides/gs/accessing-data-neo4j
 */
@Node("Person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;
    @Property
    private Integer born;
    @Property
    private String title;
    public PersonEntity(Integer born, String name) {
        this.name = name;
        this.born = born;
    }


    //@Relationship 需要指定 direction 来表明关系的方向，
    // 可选值有 Relationship.Direction.OUTGOING  从当前类指向属性所属的类
    // 和 Relationship.Direction.INCOMING，从属性所属的类指向当前类

    @Relationship(type = "1v1Rel",direction = Relationship.Direction.OUTGOING)
    private List<One2OneRelBean>  one2OneRelBeanList;

    @CreatedDate
    private Long createAt;

    @LastModifiedDate
    private Long lastModifiedAt;
}
