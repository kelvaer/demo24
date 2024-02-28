package org.example.demo2024.anno;

/**
 * @program: demo2024
 * @description: q1
 * @author: 作者名
 * @create: 2024/02/08
 */
import java.lang.annotation.*;

/**
 * 自定义日志注解
 *
 * @author xuyuxiang
 * @date 2022/6/20 14:25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonLog {

    /**
     * 日志的名称，例如:"修改菜单"
     */
    String value() default "未命名";
}

