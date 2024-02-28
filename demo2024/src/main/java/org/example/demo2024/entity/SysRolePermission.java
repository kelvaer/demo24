package org.example.demo2024.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @program: demo2024
 * @description: 角色权限
 * @author: 作者名
 * @create: 2024/02/23
 */
@Data
@Table("sys_role_permission")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRolePermission {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 数据权限
     */
    private String dataRuleIds;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 创建人
     */
    private String createdBy;

}
