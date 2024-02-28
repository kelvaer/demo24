package org.example.demo2024.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo2024.entity.SysUser;

/**
 * @program: demo2024
 * @description: login 响应
 * @author: 作者名
 * @create: 2024/02/08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    private String jwt;
    private SysUser sysUser;
}
