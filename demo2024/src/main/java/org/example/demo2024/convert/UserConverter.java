package org.example.demo2024.convert;
import org.example.demo2024.dto.UserDTO;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

    @Mapping(target = "userName", source = "name")
    UserDTO  entity2UserDTO(SysUser u);


    @Mappings({
            @Mapping(target = "uid", source = "username"),
            @Mapping(target = "name", source = "nickname")
    })
    SysUser userVo2SysUser(UserVO userVO);

    @Mappings({
            @Mapping(target = "created", source = "createTime"),
            @Mapping(target = "modified", source = "updateTime"),
            @Mapping(target = "username", source = "uid"),
            @Mapping(target = "nickname", source = "name")
    })
    UserVO sysUser2UserVO(SysUser sysUser);


    List<UserVO>  sysUsers2UserVos(List<SysUser> sysUsers);
}
