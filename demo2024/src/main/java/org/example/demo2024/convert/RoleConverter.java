package org.example.demo2024.convert;

import org.example.demo2024.entity.Role;
import org.example.demo2024.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleConverter {

    @Mappings({
            @Mapping(target = "created", source = "createTime"),
            @Mapping(target = "modified", source = "updateTime"),
            @Mapping(target = "roleName", source = "name"),
            @Mapping(target = "description", source = "remark")
    })
    List<RoleVO>  entityList2VoList(List<Role>  roles);

    @Mappings({
            @Mapping(target = "created", source = "createTime"),
            @Mapping(target = "modified", source = "updateTime"),
            @Mapping(target = "roleName", source = "name"),
            @Mapping(target = "description", source = "remark")
    })
    RoleVO  entity2Vo(Role role);
}
