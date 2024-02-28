package org.example.demo2024.api.sys;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.convert.RoleConverter;
import org.example.demo2024.dto.RoleSaveDTO;
import org.example.demo2024.entity.Role;
import org.example.demo2024.entity.table.RoleTableDef;
import org.example.demo2024.mapper.RoleMapper;
import org.example.demo2024.query.RolePageQueryReq;
import org.example.demo2024.query.RoleQueryReq;
import org.example.demo2024.vo.BasePage;
import org.example.demo2024.vo.RoleVO;
import org.example.demo2024.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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


    // sys/role/query/page

    @GetMapping("/sys/role/query/page")
    public ResultBody<BasePage<RoleVO>> findRolePage(RolePageQueryReq req) {

        QueryWrapper query = QueryWrapper.create()
                .select(RoleTableDef.ROLE.ALL_COLUMNS)
                .from(RoleTableDef.ROLE)
                .where(RoleTableDef.ROLE.ROLE_CODE.eq(req.getRoleCode())
                        .when(StrUtil.isNotBlank(req.getRoleCode())))
                .where(RoleTableDef.ROLE.ENABLED.eq(req.getEnabled())
                        .when(req.getEnabled() != null))
                .where(RoleTableDef.ROLE.NAME.like(req.getRoleName())
                        .when(StrUtil.isNotBlank(req.getRoleName())));
        Page<Role> paginate = roleMapper.paginate(req.getCurrent(), req.getSize(), query);
        List<Role> records = paginate.getRecords();
        List<RoleVO> roleVOS = roleConverter.entityList2VoList(records);
        return ResultBody.success(new BasePage<RoleVO>(req.getCurrent(), req.getSize(), paginate.getTotalRow(), roleVOS));
    }


    // sys/role/check/code?code=R01
    @GetMapping("/sys/role/check/code")
    public ResultBody<Boolean> checkRoleCode(Long id, @RequestParam(value = "code") String code) {
        QueryWrapper query = QueryWrapper.create()
                .select(RoleTableDef.ROLE.ALL_COLUMNS)
                .from(RoleTableDef.ROLE)
                .where(RoleTableDef.ROLE.ROLE_CODE.eq(code)
                        .when(StrUtil.isNotBlank(code)))
                .where(RoleTableDef.ROLE.ID.ne(id).when(id != null));
        long l = roleMapper.selectCountByQuery(query);
        return ResultBody.success(l > 0);

    }


    // sys/role/save
    @PostMapping("/sys/role/save")
    public ResultBody<Role> saveRoleSaveDTO(@RequestBody RoleSaveDTO dto) {

        QueryWrapper query = QueryWrapper.create()
                .select(RoleTableDef.ROLE.ALL_COLUMNS)
                .from(RoleTableDef.ROLE)
                .where(RoleTableDef.ROLE.ROLE_CODE.eq(dto.getRoleCode())
                        .when(StrUtil.isNotBlank(dto.getRoleCode())));
        long l = roleMapper.selectCountByQuery(query);
        if (l>0){
            return ResultBody.error("角色编码: " + dto.getRoleCode() + "重复");
        }

        String userName = "未知";
        if (StpUtil.isLogin()) {
            userName = StpUtil.getExtra("uid").toString();
        }
        Role build = Role.builder()
                .createdBy(userName)
                .roleCode(dto.getRoleCode())
                .name(dto.getRoleName())
                .remark(dto.getDescription())
                .enabled(dto.getEnabled())
                .build();
        roleMapper.insert(build);
        return ResultBody.success(build);
    }


}
