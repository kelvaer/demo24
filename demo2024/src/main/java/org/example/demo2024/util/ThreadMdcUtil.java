package org.example.demo2024.util;

/**
 * @program: demo2024
 * @description: 线程打点
 * @author: 作者名
 * @create: 2024/02/05
 */
import cn.hutool.core.lang.UUID;
import org.slf4j.MDC;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 线程mdc打点工具类
 */
public class ThreadMdcUtil {

    public static void setTRaceIdIfAbsent() {
        if (MDC.get("traceId") == null) {
            MDC.put("traceId", UUID.fastUUID().toString());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTRaceIdIfAbsent();
            try {
                //最终通过 callable.call执行线程任务
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable,final Map<String,String> context){
        return ()->{
            if (context==null){
                MDC.clear(); //mdc上下文为空 就清掉mdc
            }else {
                MDC.setContextMap(context);  // mdc上下文不为空,要设置上下文为 context
            }
            setTRaceIdIfAbsent(); //设置mdc 记录traceId
            try {
                //最终通过 runnable.run 执行线程任务
                runnable.run();
            }finally {
                MDC.clear();
            }
        };
    }
}

