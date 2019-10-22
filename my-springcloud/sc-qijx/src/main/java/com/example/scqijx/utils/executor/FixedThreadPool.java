package com.example.scqijx.utils.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qijx
 * @date 2019-09-16 19:10
 */

public class FixedThreadPool {

        public static void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            for (int i=0;i<5; i++){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //执行方法
                    }
                });
            }
            executorService.shutdown();
        }



}
