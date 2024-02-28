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

import java.time.LocalDateTime;

/**
 * @program: demo2024
 * @description: 权限
 * @author: 作者名
 * @create: 2024/02/22
 */
@Data
@Table("sys_permission")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission {
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
    private LocalDateTime created;

    /**
     * 更新人
     */
    private String modifiedBy;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "now()")
    private LocalDateTime modified;


    /**
     * 父类
     */
    private String parentId;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 路由名字
     */
    private String name;
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单icon
     */
    private String icon;
    /**
     * 是否显示
     */
    private Integer showLink;

    public Integer getShowLink() {
        return showLink == null ? 0 : showLink;
    }

    /**
     * 排序
     */
    @Column(value = "rank")
    private Integer rank;
    /**
     * 组件
     */
    private String component;
    /**
     * 是否显示父菜单
     */
    private Integer showParent;

    public Integer getShowParent() {
        return showParent == null ? 0 : showParent;
    }

    /**
     * 是否缓存该路由页面（开启后，会保存该页面的整体状态，刷新后会清空状态）
     */
    private Integer keepAlive;

    public Integer getKeepAlive() {
        return keepAlive == null ? 0 : keepAlive;
    }

    /**
     * 是否iframe
     */
    private Integer isFrame;

    public Integer getIsFrame() {
        return isFrame == null ? 0 : isFrame;
    }

    /**
     * 类型（0：目录；1：菜单 ；2：按钮权限）
     */
    private Integer menuType;
    /**
     * 菜单权限编码，例如：“sys:schedule:list,sys:schedule:info”,多个逗号隔开
     */
    private String perms;
    /**
     * 是否启用
     */
    @Column("is_enable")
    private Integer enabled;
}
