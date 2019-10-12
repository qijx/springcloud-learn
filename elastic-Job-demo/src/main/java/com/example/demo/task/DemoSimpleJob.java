package com.example.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import lombok.extern.slf4j.Slf4j;

/** 
* @author guozhong
* @version 创建时间：2019年5月16日 上午10:43:48 
* 
*/
@Component
@Slf4j
public class DemoSimpleJob implements SimpleJob,CommandLineRunner {
	
	@Autowired
    private ZookeeperRegistryCenter regCenter;
	
	@Value("${elastic.job.demoSimpleJob.jobName}")
	private  String jobName;
	
	@Value("${elastic.job.demoSimpleJob.cron}")
	private String cron;
	
	@Value("${elastic.job.demoSimpleJob.shardingTotalCount}")
	private Integer shardingTotalCount;
	
	@Value("${elastic.job.demoSimpleJob.shardingItemParameters}")
	private String shardingItemParameters;
	
	@Value("${elastic.job.demoSimpleJob.failover}")
	private Boolean failover;
	
	@Autowired
	private DemoService demoService;

	
	@Override
	public void execute(ShardingContext shardingContext) {
		// 任务执行入口
		log.info("------Thread ID : {},任务总片数: {}, "
				+"当前分片项:{},当前参数. {}"+"当前任务名称:{},当前任务参数:{},当前任务的id:{}", 
				//获取当前线程的id
				Thread.currentThread().getId(),
				//获取任务总片数
				shardingContext.getShardingTotalCount(),
				//获取当前分片项
				shardingContext.getShardingItem(),
				//获取当前的参数
				shardingContext.getShardingParameter(),
				//获取当前任务名称
				shardingContext.getJobName(),
				//获取当前任务参数
				shardingContext.getJobParameter(),
				//获取任务的id
				shardingContext.getTaskId()
				);
		demoService.log(shardingContext);
	}

	@Override
	public void run(String... args) throws Exception {
		// spring 启动完成后初始化job
		JobScheduler demoSimpleJob = JobConfigurationUtil.simpleJobScheduler(this, jobName, cron, shardingTotalCount, 
				shardingItemParameters, regCenter, failover);
		demoSimpleJob.init();
	}
	
	


}
