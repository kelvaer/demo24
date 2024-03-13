package org.example.demo2024.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.demo2024.anno.RedisLimit;
import org.example.demo2024.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: demo2024
 * @description: redis限流切面
 * @author: 作者名
 * @create: 2024/02/26
 */
@Slf4j
@Aspect
@Component
public class RedisLimitAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Pointcut("@annotation(redisLimit)")
    public void pointcut(RedisLimit redisLimit) {}


    @Before("pointcut(redisLimit)")
    public void before(JoinPoint joinPoint, RedisLimit redisLimit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ip = IpUtil.getIp(request);
        String key = redisLimit.key().equals("") ? ip : redisLimit.key();
        int limit = redisLimit.limit();
        int timeout = redisLimit.timeout();
        if (!redisLimit(redisTemplate, key, limit, timeout)) {
            throw new RuntimeException("限流了");
        }
    }



    /**
     * 判断Redis中的key对应的值，是否满足小于limit
     *
     * @param redisTemplate RedisTemplate
     * @param key           键
     * @param limit         限流次数
     * @param timeout       超时时间（秒）
     * @return 是否限流
     */
    private boolean redisLimit(RedisTemplate<String, String> redisTemplate, String key, int limit, int timeout) {
        String value = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            redisTemplate.watch(key);
            List<String> list = redisTemplate.opsForList().range(key, 0, -1);
            int count = 0;
            if (list != null && !list.isEmpty()) {
                for (String time : list) {
                    if (Long.parseLong(time) >= (System.currentTimeMillis() / 1000 - timeout)) {
                        count++;
                    } else {
                        redisTemplate.opsForList().trim(key, count, -1);
                        break;
                    }
                }
            }
            if ((count + 1) > limit) {
                return false;
            }
//            redisTemplate.multi();
//            redisTemplate.opsForList().rightPush(key, value);
//            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
//            redisTemplate.exec();


            List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    redisTemplate.opsForList().rightPush(key, value);
                    redisTemplate.expire(key, timeout, TimeUnit.SECONDS);

                    // This will contain the results of all operations in the transaction
                    return operations.exec();
                }
            });
            log.info("txResults:{}",txResults);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("redisLimit切面异常",e);
            return false;
        } finally {
            redisTemplate.unwatch();
        }
        return true;
    }


}
