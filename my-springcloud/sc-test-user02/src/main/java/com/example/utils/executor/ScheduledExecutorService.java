package com.example.utils.executor;//package com.example.springboot.demo.threadPool.executor;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author qijx
// * @date 2019-09-16 19:31
// */
//
//public class ScheduledExecutorService {
//        public static void main(String[] args) {
//            // 1. 创建 定时线程池对象 & 设置线程池线程数量固定为5
//            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
//            // 2. 创建好Runnable类线程对象 & 需执行的任务
//            Runnable task =new Runnable(){
//                public void run() {
//                    System.out.println("执行任务啦");
//                }
//            };
//            // 3. 向线程池提交任务
//            executorService.schedule(task, 1, TimeUnit.SECONDS); // 延迟1s后执行任务
//            executorService.scheduleAtFixedRate(task,10,1000,TimeUnit.MILLISECONDS);// 延迟10ms后、每隔1000ms执行任务
//        }
//
//}
