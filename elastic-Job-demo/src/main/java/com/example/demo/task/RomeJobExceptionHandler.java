package com.example.demo.task;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RomeJobExceptionHandler implements JobExceptionHandler {

	@Override
	public void handleException(String jobName, Throwable cause) {
		// TODO Auto-generated method stub
		log.error("任务名称 :{} 执行失败,原因 : {},请关注!",jobName,cause.getLocalizedMessage());
	}

}
