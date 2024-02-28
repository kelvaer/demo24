package org.example.demo2024.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: demo2024
 * @description: role page
 * @author: 作者名
 * @create: 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageQueryReq extends RoleQueryReq{
    private long size;
    private long current;
}
