package org.example.demo2024.query;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @program: demo2024
 * @description: 1
 * @author: 作者名
 * @create: 2024/02/27
 */
@Data
public class RoleQueryReq {
    /**
     * 角色code
     */
    @Parameter(description = "角色code")
    private String roleCode;
    /**
     * 角色name
     */
    @Parameter(description = "角色name")
    private String roleName;
    /**
     * 是否启用
     */
    @Parameter(description = "是否启用")
    private Integer enabled;
}
