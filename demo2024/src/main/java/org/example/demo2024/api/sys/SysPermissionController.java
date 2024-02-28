package org.example.demo2024.api.sys;

import cn.hutool.core.collection.CollUtil;
import org.example.demo2024.entity.SysRolePermission;
import org.example.demo2024.entity.table.SysRolePermissionTableDef;
import org.example.demo2024.enums.MenuTypeEnums;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.anno.CommonLog;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.dto.PermissionTree;
import org.example.demo2024.entity.SysPermission;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.entity.table.SysPermissionTableDef;
import org.example.demo2024.mapper.SysPermissionMapper;
import org.example.demo2024.mapper.SysRolePermissionMapper;
import org.example.demo2024.mapper.SysUserMapper;
import org.example.demo2024.query.PermissionTreeQuery;
import org.example.demo2024.vo.VueMenuRouteMeta;
import org.example.demo2024.vo.VueMenuRouteVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: demo2024
 * @description: 权限菜单
 * @author: 作者名
 * @create: 2024/02/22
 */
@Slf4j
@Api(tags = "权限类API")
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @GetMapping("/tree")
    public ResultBody<List<PermissionTree>> getMenuTree(PermissionTreeQuery req) {

        QueryWrapper query = QueryWrapper.create()
                .select(SysPermissionTableDef.SYS_PERMISSION.ALL_COLUMNS)
                .from(SysPermissionTableDef.SYS_PERMISSION)
                .where(SysPermissionTableDef.SYS_PERMISSION.TITLE.like(req.getTitle())
                        .when(StrUtil.isNotBlank(req.getTitle())))
                .where(SysPermissionTableDef.SYS_PERMISSION.ENABLED.eq(req.getEnabled())
                        .when(req.getEnabled() != null))
                .orderBy(SysPermissionTableDef.SYS_PERMISSION.RANK, true);
        List<SysPermission> sysPermissions = sysPermissionMapper.selectListByQuery(query);


        return null;
    }


    /**
     * 构建菜单路由
     *
     * @param permissionTree 菜单路由权限树
     * @return 菜单路由
     */
    private VueMenuRouteVO buildRoute2(PermissionTree permissionTree) {
        VueMenuRouteVO menuRoute = new VueMenuRouteVO();
        Integer menuType = permissionTree.getMenuType();
        //  路由地址 必须有个 `/`
        menuRoute.setPath(StrUtil.addPrefixIfNot(permissionTree.getPath(), "/"));
        // 路由名字（必须保持唯一）
        menuRoute.setName(permissionTree.getName());
        // 元信息
        VueMenuRouteMeta routeMeta = new VueMenuRouteMeta();
        // 菜单名称
        routeMeta.setTitle(permissionTree.getTitle());
        // 菜单图标
        routeMeta.setIcon(permissionTree.getIcon());
        // 是否显示
        routeMeta.setShowLink(permissionTree.getShowLink() == 1);
        // 目录
        if (MenuTypeEnums.dir.getValue().equals(menuType)) {
            // 菜单排序，值越高排的越后（只针对顶级路由）
            // TODO 其实这里已经时有序取出了，所以可以不需要给RouteMeta设置rank
            routeMeta.setRank(permissionTree.getRank());
        }
        //菜单
        else if (MenuTypeEnums.menu.getValue().equals(menuType)) {
            // 按需加载需要展示的页面 不需要 '/'
            menuRoute.setComponent(StrUtil.removePrefix(permissionTree.getComponent(), "/"));
            //  是否显示父级菜单
            routeMeta.setShowParent(permissionTree.getShowParent() == 1);
            // 是否缓存该路由页面
            routeMeta.setKeepAlive(permissionTree.getKeepAlive() == 1);
            // 需要内嵌的iframe链接地址
            if (permissionTree.getIsFrame() == 1) {
                routeMeta.setFrameSrc(permissionTree.getPath());
            }
        }
        menuRoute.setMeta(routeMeta);

        if (CollectionUtil.isNotEmpty(permissionTree.getChildren())) {
            menuRoute.setChildren(buildRoute(permissionTree.getChildren()));
        }
        return menuRoute;
    }

    /**
     * 构建菜单路由
     *
     * @param permissionTrees 菜单路由权限树集合
     * @return 菜单路由
     */
    public List<VueMenuRouteVO> buildRoute(@Nonnull List<PermissionTree> permissionTrees) {
        List<VueMenuRouteVO> menuRouteList = new ArrayList<>();
        for (PermissionTree permissionTree : permissionTrees) {
            menuRouteList.add(buildRoute2(permissionTree));
        }
        return menuRouteList;
    }

    /**
     * 将菜单路由权限集合构建为树形
     *
     * @param permissions 菜单路由权限
     * @return .
     */
    public List<PermissionTree> buildTree(List<SysPermission> permissions) {
        List<PermissionTree> permissionTrees = BeanUtil.copyToList(permissions, PermissionTree.class);
        List<PermissionTree> trees = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (PermissionTree permissionTree : permissionTrees) {
            if (StrUtil.isBlank(permissionTree.getParentId())) {
                trees.add(permissionTree);
            }
            for (PermissionTree tree : permissionTrees) {
                if (permissionTree.getId().equals(tree.getParentId())) {
                    if (permissionTree.getChildren() == null) {
                        permissionTree.setChildren(new ArrayList<>());
                    }
                    permissionTree.getChildren().add(tree);

                    ids.add(tree.getId());
                }
            }
        }
        if (trees.size() == 0) {
            trees = permissionTrees.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    @CommonLog(value = "获取当前用户路由")
    @GetMapping("/routes")
    @ApiOperation(value = "系统权限", notes = "获取当前用户路由")
    public ResultBody<List<VueMenuRouteVO>> getCurrentRoute() {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        log.info("登录用户账号:{}", loginIdAsString);
        SysUser sysUser = sysUserMapper.selectOneById(loginIdAsString);
        log.info("登录用户:{}", sysUser);
        if (sysUser.isManager()) {
            QueryWrapper query1 = QueryWrapper.create()
                    .select()
                    .where(SysPermissionTableDef.SYS_PERMISSION.ENABLED.eq(1)
                            .and(SysPermissionTableDef.SYS_PERMISSION.MENU_TYPE.in(0, 1))
                    ).orderBy(SysPermissionTableDef.SYS_PERMISSION.RANK, true);
            List<SysPermission> sysPermissions = sysPermissionMapper.selectListByQuery(query1);
            if (null == sysPermissions) {
                return ResultBody.success(Collections.emptyList());
            }
            List<PermissionTree> trees = buildTree(sysPermissions);
            return ResultBody.success(buildRoute(trees));

        } else {
            //用户的角色
            //current_user_roles
            List<Long> collectRoleIds = (List<Long>) StpUtil.getExtra("current_user_roles");
            QueryWrapper query3 = QueryWrapper.create()
                    .select().where(SysRolePermissionTableDef.SYS_ROLE_PERMISSION.ROLE_ID.in(collectRoleIds));
            List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectListByQuery(query3);
            if (CollUtil.isEmpty(sysRolePermissions)) {
                throw new RuntimeException("角色未配置权限菜单");
            }
            List<String> collectPermissionIds = sysRolePermissions.stream()
                    .map(SysRolePermission::getPermissionId).collect(Collectors.toList());
            List<SysPermission> sysPermissions = sysPermissionMapper.selectListByIds(collectPermissionIds);
            log.info("用户的菜单:{}", sysPermissions);
            if (null == sysPermissions) {
                return ResultBody.success(Collections.emptyList());
            }
            List<SysPermission> dbPermList = new ArrayList<>();
            for (SysPermission sysPermission : sysPermissions) {
                //目录
                if (sysPermission.getEnabled() == 1 && sysPermission.getMenuType() == 0) {
                    dbPermList.add(sysPermission);
                }
                //菜单
                if (sysPermission.getEnabled() == 1 && sysPermission.getMenuType() == 1) {
                    dbPermList.add(sysPermission);
                }
            }

            List<PermissionTree> trees = buildTree(dbPermList);
            return ResultBody.success(buildRoute(trees));


        }


    }


}
