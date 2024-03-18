package org.example.demo2024.cfg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: demo2024
 * @description: async任务线程池配置
 * @author: 作者名
 * @create: 2024/02/05
 */
@Configuration
@Slf4j
public class AsyncConfig {

    @Bean("asyncVoidTestTaskExecutor")
    public AsyncThreadPoolMdcWrapper asyncVoidTaskTest() {
        AsyncThreadPoolMdcWrapper executor = new AsyncThreadPoolMdcWrapper();
        //核心线程数5：线程池创建时候初始化的线程数
        executor.setCorePoolSize(5);
        //最大线程数10：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(10);
        //缓冲队列100：用来缓冲执行任务的队列
        executor.setQueueCapacity(100);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("voidTestTaskAsync-");
        //线程池满了后新任务由 任务发起者的线程执行
        RejectedExecutionHandler callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        executor.setRejectedExecutionHandler(callerRunsPolicy);
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        log.info("asyncVoidTestTaskExecutor 初始化完成!");
        return executor;
    }

//    版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。
//    原文链接：https://blog.csdn.net/ThinkPet/article/details/131056402


}
