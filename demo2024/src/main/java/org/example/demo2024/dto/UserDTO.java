package org.example.demo2024.dto;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.util.Date;

/**
 * @program: demo2024
 * @description: user dto
 * @author: 作者名
 * @create: 2024/02/05
 */
@Data
public class UserDTO {
    private Long id;
    private String userName;
    private Integer age;
    private String email;
    private String uid;
    private String pwd;
    private String remark;

    private Date createTime;
    private Date updateTime;
}
