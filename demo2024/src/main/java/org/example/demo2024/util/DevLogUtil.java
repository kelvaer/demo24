package org.example.demo2024.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.dialect.Props;
import com.antherd.smcrypto.sm2.Sm2;
import org.aspectj.lang.JoinPoint;
import org.example.demo2024.anno.CommonLog;
import org.example.demo2024.biz.IDevLogService;
import org.example.demo2024.entity.DevLog;
import org.example.demo2024.enums.DevLogCategoryEnum;
import org.example.demo2024.enums.DevLogExeStatusEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: demo2024
 * @description: 1w
 * @author: 作者名
 * @create: 2024/02/18
 */
public class DevLogUtil {

    private static final Props props = new Props("application.properties");


    private static final IDevLogService devLogService = SpringUtil.getBean(IDevLogService.class);

    public static void executeOperationLog(CommonLog commonLog, String userName, JoinPoint joinPoint, String resultJson) throws Exception {
        HttpServletRequest request = CommonServletUtil.getRequest();
        DevLog devLog = genBasOpLog();
        ThreadUtil.execute(() -> {
            devLog.setCategory(DevLogCategoryEnum.OPERATE.getValue());
            devLog.setName(commonLog.value());
            devLog.setExeStatus(DevLogExeStatusEnum.SUCCESS.getValue());
            devLog.setClassName(joinPoint.getTarget().getClass().getName());
            devLog.setMethodName(joinPoint.getSignature().getName());
            devLog.setReqMethod(request.getMethod());
            devLog.setReqUrl(request.getRequestURI());
            devLog.setParamJson(CommonServletUtil.getArgsJsonString(joinPoint));
            devLog.setResultJson(resultJson);
            devLog.setOpTime(DateTime.now());
            devLog.setOpUser(userName);
            creatLogSignValue(devLog);
            devLogService.save(devLog);
        });
    }

    /**
     * 记录异常日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:31
     */
    public static void executeExceptionLog(CommonLog commonLog, String userName, JoinPoint joinPoint, Exception exception) throws Exception {
        HttpServletRequest request = CommonServletUtil.getRequest();
        DevLog devLog = genBasOpLog();
        ThreadUtil.execute(() -> {
            devLog.setCategory(DevLogCategoryEnum.EXCEPTION.getValue());
            devLog.setName(commonLog.value());
            devLog.setExeStatus(DevLogExeStatusEnum.FAIL.getValue());
            devLog.setExeMessage(ExceptionUtil.stacktraceToString(exception, Integer.MAX_VALUE));
            devLog.setClassName(joinPoint.getTarget().getClass().getName());
            devLog.setMethodName(joinPoint.getSignature().getName());
            devLog.setReqMethod(request.getMethod());
            devLog.setReqUrl(request.getRequestURI());
            devLog.setParamJson(CommonServletUtil.getArgsJsonString(joinPoint));
            devLog.setOpTime(DateTime.now());
            devLog.setOpUser(userName);
            creatLogSignValue(devLog);
            devLogService.save(devLog);
        });
    }

    /**
     * 构建日志完整性保护签名数据
     */
    private static void creatLogSignValue (DevLog devLog) {
        String logStr = devLog.toString().replaceAll(" +","");
        String publicKey = props.getProperty("sm2.public-key");

        String encryptData = Sm2.doEncrypt(logStr, publicKey); // 加密结果

        devLog.setSignData(encryptData);
    }

    private static DevLog genBasOpLog() throws Exception {
        HttpServletRequest request = CommonServletUtil.getRequest();
        String ip = Ip2regionUtil.getIp(request);
        String loginId;
        try {
            loginId = StpUtil.getLoginIdAsString();
            if (ObjectUtil.isEmpty(loginId)) {
                loginId = "-1";
            }
        } catch (Exception e) {
            loginId = "-1";
        }
        DevLog devLog = new DevLog();
        devLog.setOpIp(ip);
        devLog.setOpAddress(Ip2regionUtil.getAddr(ip));
        devLog.setOpBrowser(CommonServletUtil.getBrowser(request));
        devLog.setOpOs(CommonServletUtil.getOs(request));
        devLog.setCreateUser(loginId);
        return devLog;
    }
}
