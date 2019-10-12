package com.example.springboot.demo.threadPool.methodTwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author qijx
 * @date 2019-07-03 17:40
 */

@Service
public class ThreadPoolService2 {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolService2.class);

    /**
     * @Async该注解不需要在指定任何bean
     */
    @Async
    public void executeAsync() {
        logger.info("start executeAsync");
        try {
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }


}
