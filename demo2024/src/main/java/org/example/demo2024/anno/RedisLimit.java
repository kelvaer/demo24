package org.example.demo2024.anno;

import java.lang.annotation.*;

/**
 *  @RedisLimit(key = "testKey", limit = 5, timeout = 60)
 * 在使用`@RedisLimit`注解时，每个方法对应的key不应该相同，否则会出现相互干扰的情况
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLimit {

    /**
     * 限流次数
     * @return int
     */
    int limit() default 10;

    /**
     * 超时时间（秒）
     * @return int
     */
    int timeout() default 60;

    /**
     * 每个方法对应的key不应该相同，否则会出现相互干扰的情况
     * @return String
     */
    String key() default "";
}
