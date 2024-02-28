package org.example.demo2024.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: demo2024
 * @description: user vo
 * @author: 作者名
 * @create: 2024/02/27
 */
@Data
public class UserVO {

    private Long id;


    private String createdBy;

    private Date created;


    private String modifiedBy;


    private Date modified;

    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private Integer isEnabled;

    public boolean isEnabled() {
        if (null == isEnabled) {
            return false;
        }
        return isEnabled == 1;
    }

    private String orgId;
    private String orgCode;
    private String orgName;
    private Integer isManage;
    private List<Long> roleIds;


}
