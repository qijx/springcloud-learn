package com.example.demo.task;


import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties.JobPropertiesEnum;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobConfigurationUtil {

    public static JobScheduler simpleJobScheduler(SimpleJob simpleJob, String jobName, String cron, int shardingTotalCount,
            String shardingItemParameters,ZookeeperRegistryCenter zookeeperRegistryCenter,boolean failover) {
        log.info(String.format("任务名称 : %s,任务总片数: %s, " + "参数: %s," + "任务配置: %s", jobName,shardingTotalCount, shardingItemParameters,
                cron));
        return new SpringJobScheduler(simpleJob, zookeeperRegistryCenter, getLiteJobConfiguration(simpleJob.getClass(), jobName, cron,
                shardingTotalCount, shardingItemParameters,failover));
    }
    
    
    public static <T> JobScheduler dataFlowJobScheduler(DataflowJob<T> dataFlowJob, String jobName, String cron, int shardingTotalCount,
            String shardingItemParameters,ZookeeperRegistryCenter zookeeperRegistryCenter,boolean failover) {
        log.info(String.format("任务名称 : %s,任务总片数: %s, " + "参数: %s," + "任务配置: %s",jobName, shardingTotalCount, shardingItemParameters,
                cron));
        return new SpringJobScheduler(dataFlowJob, zookeeperRegistryCenter, getLiteJobConfigurationForDataFlow(dataFlowJob.getClass(), jobName, cron,
                shardingTotalCount, shardingItemParameters,failover));
    }

    /**
     * 
     * @param <T>
     * @param jobClass
     *            实现SimpleJob接口的实例
     * @param jobName
     *            定时任务名称
     * @param cron
     *            任务启动时间 格式 "0/20 * * * * ?"
     * @param shardingTotalCount
     *            分片数
     * @param shardingItemParameters
     *            任务参数 ，例子：0=A,1=B
     * @return
     */
    private static <T> LiteJobConfiguration getLiteJobConfiguration(Class<? extends SimpleJob> jobClass, String jobName,
            String cron, int shardingTotalCount, String shardingItemParameters,boolean failover) {

        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
            	.jobProperties(JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), RomeJobExceptionHandler.class.getCanonicalName())
				.failover(failover)
                .shardingItemParameters(shardingItemParameters).jobParameter(shardingItemParameters).build(),
                jobClass.getCanonicalName())).overwrite(true).build();

    }
    
    
    private static LiteJobConfiguration getLiteJobConfigurationForDataFlow(Class<? extends DataflowJob> jobClass, String jobName,
            String cron, int shardingTotalCount, String shardingItemParameters,boolean failover) {

        return LiteJobConfiguration.newBuilder(
        		new DataflowJobConfiguration(
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
            	.jobProperties(JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), RomeJobExceptionHandler.class.getCanonicalName())
				.failover(failover)
                .shardingItemParameters(shardingItemParameters).jobParameter(shardingItemParameters).build(),
                jobClass.getCanonicalName(),true)).overwrite(true).build();

    }

}
