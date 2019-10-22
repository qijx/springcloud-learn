package com.example.scqijx.utils.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qijx
 * @date 2019-09-16 19:41
 */

public class singleThreadExecutor {
        public static void main(String[] args) {
            ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
            for (int i=0;i<5; i++){
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        //执行方法
                    }
                });
            }
            singleThreadExecutor.shutdown();
        }

}
