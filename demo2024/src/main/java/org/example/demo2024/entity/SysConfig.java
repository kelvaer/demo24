package org.example.demo2024.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: demo2024
 * @description: sys config
 * @author: 作者名
 * @create: 2024/02/28
 */
@Data
@Table("sys_config")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig {

    /**
     * ID
     */
    @Id(keyType = KeyType.Generator,value = KeyGenerators.flexId)
    private String id;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private Date created;

    /**
     * 更新人
     */
    private String modifiedBy;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "now()")
    private Date modified;

    /**
     * 类型
     * <ul>
     *     <li>1. 系统配置</li>
     *     <li>2. 业务配置</li>
     * </ul>
     */
    private Integer type;
    /**
     * 配置项
     */
    private String code;
    /**
     * 配置值
     */
    private String value;
    /**
     * 备注,max 255
     */
    private String memo;
    


}
