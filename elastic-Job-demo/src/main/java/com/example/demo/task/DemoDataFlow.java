package com.example.demo.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import lombok.extern.slf4j.Slf4j;

/** 
* @author guozhong
* @version 创建时间：2019年5月16日 上午11:46:17 
* 流式作业demo
*/
@Component
@Slf4j
public class DemoDataFlow implements DataflowJob<Integer>,CommandLineRunner {

	@Autowired
    private ZookeeperRegistryCenter regCenter;
	
	@Value("${elastic.job.demoDataFlowJob.jobName}")
	private  String jobName;
	
	@Value("${elastic.job.demoDataFlowJob.cron}")
	private String cron;
	
	@Value("${elastic.job.demoDataFlowJob.shardingTotalCount}")
	private Integer shardingTotalCount;
	
	@Value("${elastic.job.demoDataFlowJob.shardingItemParameters}")
	private String shardingItemParameters;
	
	@Value("${elastic.job.demoDataFlowJob.failover}")
	private Boolean failover;
	
	private static AtomicInteger totalCount=new AtomicInteger(10);
	@Override
	public List<Integer> fetchData(ShardingContext shardingContext) {
		// 拉取数据,如返回为null,则停止
		log.info("------Thread ID : {},任务总片数: {}, "
				+"当前分片项:{},当前参数. {}"+"当前任务名称:{},拉取数据", 
				//获取当前线程的id
				Thread.currentThread().getId(),
				//获取任务总片数
				shardingContext.getShardingTotalCount(),
				//获取当前分片项
				shardingContext.getShardingItem(),
				//获取当前的参数
				shardingContext.getShardingParameter(),
				//获取当前任务名称
				shardingContext.getJobName()
				);
		ArrayList<Integer> list=new ArrayList<>();
		Integer tempNumber=(int)(Math.random()*100);
		while (totalCount.decrementAndGet()>=0) {
			list.add(tempNumber);
		}
		return list;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<Integer> data) {
		// 处理数据
		log.info("接收到数据 : {},处理完毕",data);
	}
	
	@Override
	public void run(String... args) throws Exception {
		// 初始化job
		JobScheduler dataFlowJobScheduler = JobConfigurationUtil.dataFlowJobScheduler(this, jobName, cron, shardingTotalCount, 
				shardingItemParameters, regCenter, failover);
		dataFlowJobScheduler.init();
	}


	
	public static void resetCount(){
		totalCount.set(10);
	}

}
