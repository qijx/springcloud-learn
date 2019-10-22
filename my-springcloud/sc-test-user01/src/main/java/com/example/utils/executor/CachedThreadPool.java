package com.example.utils.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qijx
 * @date 2019-09-16 19:18
 */

public class CachedThreadPool {
        public static void main(String[] args) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            for (int i=0; i<5; i++){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //执行任务
                    }
                });
            }
            executorService.shutdown();
        }

}
