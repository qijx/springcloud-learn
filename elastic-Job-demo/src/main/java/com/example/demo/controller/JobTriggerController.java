package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.example.demo.task.DemoDataFlow;
import com.google.common.base.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "手动触发定时任务" ,description="定时任务相关")
@RequestMapping("/api/jobTrigger")
@Slf4j
public class JobTriggerController {
	
	@Value("${elaticjob.zookeeper.server-lists}")
	private String serverLists;
	
	@Value("${elaticjob.zookeeper.namespace}")
	private String namespace;
	
	
	
	@ApiOperation(value="触发定时任务,仅为测试触发任务链接",notes="根据传入的任务名称触发任务")
	@PostMapping("/{jobName}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "任务名称,用于触发指定的任务,任务名:demoSimpleJob,demoDataflow", required = true,
                paramType = "path", dataType = "String")})
	public Boolean jobTrigger(@PathVariable(name="jobName")String jobName) {
        log.info("定时任务收到触发任务请求,任务名 :{}", jobName);
        JobOperateAPI jobAPIService= JobAPIFactory.createJobOperateAPI(serverLists, namespace, Optional.fromNullable(null));
    	    jobAPIService.trigger(Optional.of(jobName.trim()), Optional.<String>absent());
    	
    	    //初始化计数器,用于演示
    	 DemoDataFlow.resetCount();
        return Boolean.TRUE;
    }
	

}
