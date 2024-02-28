package org.example.demo2024.api.sys;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.convert.RoleConverter;
import org.example.demo2024.entity.Role;
import org.example.demo2024.entity.table.RoleTableDef;
import org.example.demo2024.mapper.RoleMapper;
import org.example.demo2024.query.RoleQueryReq;
import org.example.demo2024.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: demo2024
 * @description: role
 * @author: 作者名
 * @create: 2024/02/27
 */
@Slf4j
@Api(tags = "组织-机构-部门类API")
@RestController
public class SysRoleApi {
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private RoleConverter roleConverter;

    //sys/role/query/list?enabled=1

    @GetMapping("/sys/role/query/list")
    public ResultBody<List<RoleVO>> findRoleList(RoleQueryReq req) {
        QueryWrapper query = QueryWrapper.create()
                .select(RoleTableDef.ROLE.ALL_COLUMNS)
                .from(RoleTableDef.ROLE)
                .where(RoleTableDef.ROLE.ROLE_CODE.eq(req.getRoleCode())
                        .when(StrUtil.isNotBlank(req.getRoleCode())))
                .where(RoleTableDef.ROLE.ENABLED.eq(req.getEnabled())
                        .when(req.getEnabled() != null))
                .where(RoleTableDef.ROLE.NAME.like(req.getRoleName())
                        .when(StrUtil.isNotBlank(req.getRoleName())));
        List<Role> roles = roleMapper.selectListByQuery(query);
        List<RoleVO> roleVOS = roleConverter.entityList2VoList(roles);
        return ResultBody.success(roleVOS);
    }








}
