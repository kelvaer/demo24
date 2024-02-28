package org.example.demo2024.util;

import cn.dev33.satoken.stp.StpUtil;
import org.example.demo2024.dto.SaBaseLoginUser;

/**
 * @program: demo2024
 * @description: e2
 * @author: 作者名
 * @create: 2024/02/08
 */
public class StpLoginUserUtil {
    /**
     * 获取当前B端登录用户
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:41
     **/
    public static SaBaseLoginUser getLoginUser() {
        return (SaBaseLoginUser) StpUtil.getTokenSession().get("loginUser");
    }

}
