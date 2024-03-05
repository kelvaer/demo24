package org.example.demo2024.convert;

import org.example.demo2024.entity.SysOrg;
import org.example.demo2024.vo.OrganizationVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrgConverter {


    SysOrg  vo2entity(OrganizationVO vo);


}
