package org.example.demo2024.cfg;

/**
 * @program: demo2024
 * @description: mdc打点配置
 * @author: 作者名
 * @create: 2024/02/05
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcMdcConfig implements WebMvcConfigurer {

    /**
     * 配置spring mvc启用 mvc trace拦截器，实现trace打点
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MvcTraceInterceptor())
                .addPathPatterns("/**");
    }
}

