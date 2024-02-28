package org.example.demo2024.facade;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.dto.LoginReq;
import org.example.demo2024.entity.Role;
import org.example.demo2024.entity.SysPermission;
import org.example.demo2024.entity.SysRolePermission;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.entity.SysUserRole;
import org.example.demo2024.entity.table.SysRolePermissionTableDef;
import org.example.demo2024.entity.table.SysUserRoleTableDef;
import org.example.demo2024.entity.table.SysUserTableDef;
import org.example.demo2024.mapper.RoleMapper;
import org.example.demo2024.mapper.SysPermissionMapper;
import org.example.demo2024.mapper.SysRolePermissionMapper;
import org.example.demo2024.mapper.SysUserMapper;
import org.example.demo2024.mapper.SysUserRoleMapper;
import org.example.demo2024.util.RsaUtil;
import org.example.demo2024.vo.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @program: demo2024
 * @description: login facade
 * @author: 作者名
 * @create: 2024/02/26
 */
@Service
@Slf4j
public class SysLoginFacade {
    @Value("${security.login.rsa-private-key}")
    private String rsaPriKey;
    @Resource
    private SysUserMapper  sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    public LoginResponse execDoLogin(LoginReq req) {
        String pwd = RsaUtil.decryptByPrivateKey(req.getPassword(),rsaPriKey);
        log.info("pwd解密:{}",pwd);
        QueryWrapper query1 = QueryWrapper.create()
                .select()
                .where(SysUserTableDef.SYS_USER.UID.eq(req.getUsername())
                        .and(SysUserTableDef.SYS_USER.PWD.eq(pwd)));
        SysUser sysUser = sysUserMapper.selectOneByQuery(query1);
        if (sysUser==null){
            throw new RuntimeException("用户不存在");
        }

        SaLoginModel sm = new SaLoginModel();
        sm.setExtra("user_id", sysUser.getId());
        sm.setExtra("user_nickname", sysUser.getNickname());
        sm.setExtra("user_cn_name", sysUser.getName());
        sm.setExtra("uid", sysUser.getUid());


        //用户 关联的 角色
        QueryWrapper query2 = QueryWrapper.create()
                .select().where(SysUserRoleTableDef.SYS_USER_ROLE.USER_ID.eq(sysUser.getId()));
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectListByQuery(query2);
        if (CollUtil.isEmpty(sysUserRoles)){
            throw new RuntimeException("用户未配置角色");
        }
        List<Long> collectRoleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        sm.setExtra("current_user_roles", collectRoleIds);
        List<Role> roles = roleMapper.selectListByIds(collectRoleIds);
        log.info("用户的角色:{}",roles);

        //角色 关联的 权限菜单
        QueryWrapper query3 = QueryWrapper.create()
                .select().where(SysRolePermissionTableDef.SYS_ROLE_PERMISSION.ROLE_ID.in(collectRoleIds));
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectListByQuery(query3);
        if (CollUtil.isEmpty(sysRolePermissions)){
            throw new RuntimeException("角色未配置权限菜单");
        }
        List<String> collectPermissionIds = sysRolePermissions.stream()
                .map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        List<SysPermission> sysPermissions = sysPermissionMapper.selectListByIds(collectPermissionIds);
        log.info("用户的菜单:{}",sysPermissions);
        List<String> permissions = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissions) {
            if (StrUtil.isNotBlank(sysPermission.getPerms())){
                permissions.add(sysPermission.getPerms());
            }
        }
        //登录 & 持久化登录用户相关信息
        StpUtil.login(sysUser.getId(), sm);
        LoginResponse build = LoginResponse.builder()
                .username(req.getUsername())
                .nickname(sysUser.getNickname())
                .token(StpUtil.getTokenInfo().getTokenValue())
                .permissions(permissions)
                .build();
        log.info("LoginResponse token:{}",build.getToken());
        return build;
    }


}
