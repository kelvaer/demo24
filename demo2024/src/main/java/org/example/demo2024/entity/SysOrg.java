package org.example.demo2024.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @program: demo2024
 * @description: 组织部门
 * @author: 作者名
 * @create: 2024/02/26
 */
@Data
@Table("sys_org")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysOrg {

    @Id(keyType = KeyType.Generator,value = KeyGenerators.flexId)
    private String id;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    protected String createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    protected LocalDateTime created;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    protected String modifiedBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    protected LocalDateTime modified;

    /**
     * 父机构ID
     */
    private String parentId;
    /**
     * 机构path
     */
    private String path;
    /**
     * 机构代码
     */
    private String code;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 联系人
     */
    private String linkMan;

    /**
     * 联系电话
     */
    private String linkTel;

    /**
     * 省份编码
     */
    private String provinceId;

    /**
     * 省份名称
     */
    private String province;

    /**
     * 城市编码
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 区县编码
     */
    private String countyId;

    /**
     * 区县名称
     */
    private String county;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 详细地址的经纬度
     */
    private String longitude;

    /**
     * 备注
     */
    private String memo;
    /**
     * 是否内置
     */
    private Integer isSystem;

    /**
     * 是否启用
     */
    private Integer isEnable;
}
