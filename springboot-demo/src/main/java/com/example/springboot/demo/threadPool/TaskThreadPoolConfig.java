package com.example.springboot.demo.threadPool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author qijx
 * @date 2019-07-03 20:00
 */
@Component
@ConfigurationProperties(prefix = "task.pool")
@Data
public class TaskThreadPoolConfig {
    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;

}
