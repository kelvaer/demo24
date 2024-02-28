package org.example.demo2024.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author oppor
 */
@Data
@Table("role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id(keyType = KeyType.Auto)
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 是否启用（1，启用，2禁用）
     */
    @Column(value = "is_enable")
    private Integer enabled;

    public Integer getEnabled() {
        if (null == enabled) {
            return 0;
        }
        return enabled;
    }

    private String remark;

    @Column(onInsertValue = "now()")
    private Date createTime;
    @Column(onUpdateValue = "now()")
    private Date updateTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String modifiedBy;


    /**
     * 总部标识：1是，0：否
     */
    @Column(value = "is_system")
    private Integer isSystem;


    public Integer getIsSystem() {
        if (null == isSystem) {
            return 0;
        }
        return isSystem;
    }
}
