
package com.example.demo.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
public class ElasticJobAutoConfiguration {

	@Value("${elaticjob.zookeeper.server-lists}")
	private String serverLists;
	
	@Value("${elaticjob.zookeeper.namespace}")
	private String namespace;
	
	@Value("${elaticjob.zookeeper.sessiontimeout}")
	private Integer zkSessionTimeout;
	
	@Bean 
	public ZookeeperRegistryCenter zookeeperRegistryCenter(){
		 ZookeeperConfiguration config = new ZookeeperConfiguration(serverLists, namespace);
	        config.setSessionTimeoutMilliseconds(zkSessionTimeout);
	        ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(config);
	        regCenter.init();
	        return regCenter;
	}
}
