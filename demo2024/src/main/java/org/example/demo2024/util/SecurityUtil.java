package org.example.demo2024.util;

import cn.dev33.satoken.stp.StpUtil;

/**
 * @program: demo2024
 * @description: 安全控制
 * @author: 作者名
 * @create: 2024/03/05
 */
public class SecurityUtil {

    public static String getCurrentUserName() {
        String userName = "未知";
        if (StpUtil.isLogin()) {
            userName = StpUtil.getExtra("uid").toString();
        }
        return userName;
    }
}
