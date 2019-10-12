package com.example.springboot.demo.threadPool.methodTwo;

import com.example.springboot.demo.threadPool.TaskThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qijx
 * @date 2019-07-03 19:47
 */
@Configuration
public class NativeTaskExecutePool implements AsyncConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(NativeTaskExecutePool.class);
    /**
     * 注入配置类
     */
    @Autowired
    private TaskThreadPoolConfig config;

    /**
     * 1.实现AsyncConfigurer重写spring默认的线程池方法
     * 2.这种形式的线程池配置是需要在使用的方法上面@Async但不在需要指定配置类了
     * 注意：完全可以把线程池的参数写到配置文件中
     */

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(config.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(config.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(config.getQueueCapacity());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        // 设置默认线程名称
        executor.setThreadNamePrefix("NativeAsyncTaskExecutePool-");
        // 设置拒绝策略rejection-policy：当pool已经达到max size的时候，如何处理新任务 CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }


    /**
     * 异步任务中异常处理
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
                logger.error("==========================" + arg0.getMessage() + "=======================", arg0);
                logger.error("exception method:" + arg1.getName());
            }
        };
    }
}
