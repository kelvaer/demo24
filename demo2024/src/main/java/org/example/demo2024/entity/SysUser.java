package org.example.demo2024.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oppor
 */
@Table("user")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String uid;
    private String pwd;
    private String remark;

    @Column(onInsertValue = "now()")
    private Date createTime;

    @Column(onUpdateValue = "now()")
    private Date updateTime;

    private String nickname;

    private String phone;

    private String createdBy;

    private String modifiedBy;

    @Column(value = "is_enabled")
    private Integer isEnabled;


    private String orgId;

    @Column(value = "is_manage")
    private Integer isManage;


    public boolean isManager() {
        return isManage == 1;
    }
}