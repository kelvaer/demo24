package org.example.demo2024.dto;

import lombok.Data;

/**
 * @program: demo2024
 * @description: role save
 * @author: 作者名
 * @create: 2024/02/28
 */
@Data
public class RoleSaveDTO {

    private String roleName;
    private String roleCode;
    private String description;
    private Integer enabled;


}
