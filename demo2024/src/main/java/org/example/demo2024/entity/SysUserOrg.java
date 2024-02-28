package org.example.demo2024.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: demo2024
 * @description: 用户组织关系
 * @author: 作者名
 * @create: 2024/02/26
 */
@Data
@Table("sys_user_org")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserOrg {


    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long userId;

    private String orgId;
}