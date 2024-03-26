package org.example.demo2024.neo4j.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @program: demo2024
 * @description: 1对1 关系
 * @author: 作者名
 * @create: 2024/03/25
 */

@RelationshipProperties  //标注 关系类
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class One2OneRelBean {
    @RelationshipId  //类似于 @Id，关系的唯一标识符
    private Long id;

    @Property
    private String value;

    @CreatedDate
    @Property
    private Long createAt;



    @TargetNode  //指定关系指向的节点实体类
    private PersonEntity personEntity;
}
