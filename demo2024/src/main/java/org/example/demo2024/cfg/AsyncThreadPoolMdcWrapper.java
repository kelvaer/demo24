package org.example.demo2024.cfg;


import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.io.Serializable;
import java.util.concurrent.*;
import org.example.demo2024.util.ThreadMdcUtil;
/**
 * @program: demo2024
 * @description: 异步线程池 的mdc 包装器，作用是 让 async 异步任务 实现trace打点
 * @author: 作者名
 * @create: 2024/02/05
 */
public class AsyncThreadPoolMdcWrapper extends ThreadPoolTaskExecutor implements Serializable {

    private static final long serialVersionUID = -1530245553055682935L;

    @Override
    public void execute(Runnable task) {
        // Runnable 类型的线程运行时  进行 mdc 打点记录
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        // Callable 类型的线程运行时  进行 mdc 打点记录
        return super.submit(ThreadMdcUtil.wrap(task,MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        //  Runnable 类型且返回Future的 线程运行时  进行 mdc 打点记录
        return super.submit(ThreadMdcUtil.wrap(task,MDC.getCopyOfContextMap()));
    }
}

