package org.example.demo2024.biz;

import com.mybatisflex.core.service.IService;
import org.example.demo2024.dto.PermissionTree;
import org.example.demo2024.entity.SysPermission;
import org.example.demo2024.vo.VueMenuRouteVO;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 获取权限ID集合 BY 角色ID结合
     *
     * @param roleIds 角色ID集合
     * @return 权限ID集合
     */
    public Set<String> listPermissionIdsByRoleIds(@Nonnull List<String> roleIds);


    /**
     * 根据ID查询已启用的权限code
     *
     * @param permissionIds ID
     * @return 已启用的权限code
     */
    public Set<String> listPermissionPreByIds(@Nonnull List<String> permissionIds);

    /**
     * 获取全部的权限信息
     *
     * @return Set<String>
     */
    public Set<String> allPermissionPre();

    /**
     * 获取全部的ID
     *
     * @return Set<String>
     */
    public Set<String> allPermissionIds();

    /**
     * 根据用户获取菜单路由
     *
     * @param userid 用户ID
     * @return 菜单路由
     */
    public List<VueMenuRouteVO> queryRouteByUserid(String userid);

    /**
     * 根据ID查询已启用的路由信息
     *
     * @param ids ID
     * @return 路由信息
     */
    public List<VueMenuRouteVO> queryRouteByIds(@Nonnull List<String> ids);


    public List<PermissionTree> buildTree(@Nonnull List<SysPermission> permissions);
}
