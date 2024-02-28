package org.example.demo2024.query;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @program: demo2024
 * @description: org tree
 * @author: 作者名
 * @create: 2024/02/27
 */
@Data
public class OrgTreeQueryReq {

    /**
     * 所属机构
     */
    @Parameter(description = "所属机构ID")
    private String parentId;
    /**
     * 机构名称
     */
    @Parameter(description = "机构名称")
    private String name;
    /**
     * 机构编码
     */
    @Parameter(description = "机构编码")
    private String code;
    /**
     * 联系人电话
     */
    @Parameter(description = "联系人电话")
    private String linkTel;
    /**
     * 状态
     */
    @Parameter(description = "状态")
    private Integer isEnable;
}
