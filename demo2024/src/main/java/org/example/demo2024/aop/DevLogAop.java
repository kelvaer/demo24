package org.example.demo2024.aop;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.demo2024.util.DevLogUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.example.demo2024.anno.CommonLog;
import java.lang.reflect.Method;

/**
 * @program: demo2024
 * @description: aop-log
 * @author: 作者名
 * @create: 2024/02/18
 */
@Aspect
@Order
@Component
public class DevLogAop {

    /**
     * 日志切入点
     *
     * @author xuyuxiang
     * @date 2020/3/23 17:10
     */
    @Pointcut("@annotation(org.example.demo2024.anno.CommonLog)")
    private void getLogPointCut() {
    }



    /**
     * 操作成功返回结果记录日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:24
     */
    @AfterReturning(pointcut = "getLogPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CommonLog commonLog = method.getAnnotation(CommonLog.class);
        String userName = "未知";
        if (StpUtil.isLogin()) {
            userName = (String) StpUtil.getExtra("uid");
        }



        // 异步记录日志
        DevLogUtil.executeOperationLog(commonLog, userName, joinPoint, JSONUtil.toJsonStr(result));
    }


    /**
     * 操作发生异常记录日志
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:24
     */
    @AfterThrowing(pointcut = "getLogPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CommonLog commonLog = method.getAnnotation(CommonLog.class);
        String userName = "未知";
        if (StpUtil.isLogin()) {
            userName = (String) StpUtil.getExtra("uid");
        }

        //异步记录日志
        DevLogUtil.executeExceptionLog(commonLog, userName, joinPoint, exception);
    }

}
