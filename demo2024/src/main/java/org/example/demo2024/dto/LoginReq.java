package org.example.demo2024.dto;

import lombok.Data;

@Data
public class LoginReq {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
