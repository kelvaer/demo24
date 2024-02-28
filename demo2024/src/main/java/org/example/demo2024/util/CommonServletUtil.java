package org.example.demo2024.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.demo2024.cfg.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: demo2024
 * @description: ewr2
 * @author: 作者名
 * @create: 2024/02/18
 */
@Slf4j
public class CommonServletUtil {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            log.error(">>> 非Web上下文无法获取Request：", e);
            throw new ServiceException("非Web上下文无法获取Request");
        }
        if (servletRequestAttributes == null) {
            throw new ServiceException("非Web上下文无法获取Request");
        } else {
            return servletRequestAttributes.getRequest();
        }
    }

    public static String getOs(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtil.isEmpty(userAgent)) {
            return StrUtil.DASHED;
        } else {
            String os = userAgent.getOs().toString();
            if (StrUtil.isNotBlank(os) && os.length() > 250) {
                os = os.substring(0, 250);
            }
            return "Unknown".equals(os) ? StrUtil.DASHED : os;
        }
    }

    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtil.isEmpty(userAgent)) {
            return StrUtil.DASHED;
        } else {
            String browser = userAgent.getBrowser().toString();
            if (StrUtil.isNotBlank(browser) && browser.length() > 250) {
                browser = browser.substring(0, 250);
            }
            return "Unknown".equals(browser) ? StrUtil.DASHED : browser;
        }
    }

    private static UserAgent getUserAgent(HttpServletRequest request) {
        String userAgentStr = ServletUtil.getHeaderIgnoreCase(request, "User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        if (ObjectUtil.isNotEmpty(userAgentStr)) {
            if ("Unknown".equals(userAgent.getBrowser().getName())) {
                userAgent.setBrowser(new Browser(userAgentStr, null, ""));
            }
        }
        return userAgent;
    }


    public static String getArgsJsonString(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        // 参数名数组
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        // 构造参数组集合
        Map<String, Object> map = MapUtil.newHashMap();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(ObjectUtil.isNotEmpty(args[i]) && isUsefulParam(args[i])) {
                if(JSONUtil.isTypeJSON(StrUtil.toString(args[i]))) {
                    map.put(parameterNames[i], JSONUtil.parseObj(args[i]));
                } else {
                    map.put(parameterNames[i], JSONUtil.toJsonStr(args[i]));
                }
            }
        }
        return JSONUtil.toJsonStr(map);
    }

    private static boolean isUsefulParam(Object arg) {
        return !(arg instanceof MultipartFile) &&
                !(arg instanceof HttpServletRequest) &&
                !(arg instanceof HttpServletResponse);
    }
}
