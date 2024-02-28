package org.example.demo2024.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: demo2024
 * @description: role vo
 * @author: 作者名
 * @create: 2024/02/27
 */
@Data
public class RoleVO {
    private Long id;
    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String modifiedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date created;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modified;



    private String roleName;

    private String roleCode;
    private String description;

    private Integer enabled;
    private Integer isSystem;
}
