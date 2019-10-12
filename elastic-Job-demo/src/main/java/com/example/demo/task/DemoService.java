package com.example.demo.task;

import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.ShardingContext;

import lombok.extern.slf4j.Slf4j;

/** 
* @author guozhong
* @version 创建时间：2019年5月16日 上午10:44:03 
* 
*/
@Service
@Slf4j
public class DemoService {

	
	public void log(ShardingContext shardingContext){
		
		log.info("任务执行");
	}
}
