package org.example.demo2024.cfg;

/**
 * @program: demo2024
 * @description: api打点
 * @author: 作者名
 * @create: 2024/02/05
 */
import cn.hutool.core.lang.UUID;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc trace拦截器，作用是 实现trace打点
 */
public class MvcTraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在请求头里拿一个 traceId ,拿不到就创建1个 traceId
        String traceId = request.getHeader("traceId");
        if (traceId==null){
            traceId = UUID.fastUUID().toString();
        }
        // traceId记录到mdc中
        MDC.put("traceId",traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //1个http请求 后台处理完毕后 ,从mdc中移除 traceId
        MDC.remove("traceId");
    }
}
