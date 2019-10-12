package com.example.springboot.demo.threadPool.methodOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qijx
 * @date 2019-07-03 15:07
 */
@Configuration
public class TaskExecutePool {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutePool.class);


    /**
     * 1.这种形式的线程池配置是需要在使用的方法上面@Async("taskExecutor"),
     * 2.如果在使用的方法上面不加该注解那么spring就会使用默认的线程池
     * 3.所以如果加@Async注解但是不指定使用的线程池，又想自己定义线程池那么就可以重写spring默认的线程池
     * 4.所以第二个方法就是重写默认线程池
     * 注意：完全可以把线程池的参数写到配置文件中
     */


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(3);
        // 设置最大线程数
        executor.setMaxPoolSize(5);
        // 设置队列容量
        executor.setQueueCapacity(5);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(300);
        // 设置默认线程名称
        executor.setThreadNamePrefix("TaskExecutePoolConfig-");
        // 设置拒绝策略rejection-policy：当pool已经达到max size的时候，如何处理新任务 CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}