package org.example.demo2024.query;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @program: demo2024
 * @description: user
 * @author: 作者名
 * @create: 2024/02/27
 */
@Data
public class UserPageQueryReq {

    /**
     * 所属机构
     */
    @Parameter(description = "所属机构")
    private String orgId;
    /**
     * 用户帐号
     */
    @Parameter(description = "用户帐号")
    private String username;
    /**
     * 姓名
     */
    @Parameter(description = "姓名")
    private String nickname;
    /**
     * 联系电话
     */
    @Parameter(description = "联系电话")
    private String phone;
    /**
     * 状态
     */
    @Parameter(description = "状态")
    private Integer enable;


    private long current;

    private long size;
}
