package org.example.demo2024.biz.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.example.demo2024.biz.ISysPermissionService;
import org.example.demo2024.dto.PermissionTree;
import org.example.demo2024.entity.SysPermission;
import org.example.demo2024.mapper.SysPermissionMapper;
import org.example.demo2024.vo.VueMenuRouteVO;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

/**
 * @program: demo2024
 * @description: 权限impl
 * @author: 作者名
 * @create: 2024/02/23
 */
@Service
public class SysPermissionServiceImpl
        extends ServiceImpl<SysPermissionMapper, SysPermission>
        implements ISysPermissionService {


    @Override
    public Set<String> listPermissionIdsByRoleIds(@Nonnull List<String> roleIds) {
        return null;
    }

    @Override
    public Set<String> listPermissionPreByIds(@Nonnull List<String> permissionIds) {
        return null;
    }

    @Override
    public Set<String> allPermissionPre() {
        return null;
    }

    @Override
    public Set<String> allPermissionIds() {
        return null;
    }

    @Override
    public List<VueMenuRouteVO> queryRouteByUserid(String userid) {
        return null;
    }

    @Override
    public List<VueMenuRouteVO> queryRouteByIds(@Nonnull List<String> ids) {
        return null;
    }

    @Override
    public List<PermissionTree> buildTree(@Nonnull List<SysPermission> permissions) {
        return null;
    }
}
