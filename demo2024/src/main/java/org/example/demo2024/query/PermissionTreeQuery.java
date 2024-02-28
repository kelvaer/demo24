package org.example.demo2024.query;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @program: demo2024
 * @description: perm tree
 * @author: 作者名
 * @create: 2024/02/27
 */
@Data
public class PermissionTreeQuery {
    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Parameter(description = "菜单名称")
    private String title;
    /**
     * 菜单状态
     */
    @Schema(description = "菜单状态")
    @Parameter(description = "菜单状态")
    private Integer enabled;
}
