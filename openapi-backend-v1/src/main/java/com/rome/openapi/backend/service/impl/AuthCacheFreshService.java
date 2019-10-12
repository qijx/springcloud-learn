/*
 * Filename AuthCacheFreshService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service.impl;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/9
 */
//@Component
@Deprecated
public class AuthCacheFreshService {
	private final Logger log = LoggerFactory.getLogger(AuthCacheFreshService.class);
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private EurekaClient eurekaClient;

	private final String AUTHSERVICE = "OPENAPI-AUTH-V1";

//	@Async
	public void cacheFresh() {
		log.info("auth cache remote fresh start ...");
		Application app = eurekaClient.getApplication(AUTHSERVICE);

		if (null != app){
			List<InstanceInfo> result = app.getInstances();
			String lastInstance = "";
			RestTemplate restTemplate = new RestTemplate();
			for(InstanceInfo instanceInfo : result) {
				lastInstance = instanceInfo.getHomePageUrl();
				String url = lastInstance + "appApi/refreshAPPAPIURI";
				log.info("auth url {} cache remote fresh start ...",url);
				try {
					restTemplate.getForObject(url, String.class);
				} catch (Exception e) {
					log.error("auth url {} cache remote fresh fail",e);
				}
				log.info("auth url {} cache remote fresh end ...",url);
			}
		}
		/*List<ServiceInstance> list = discoveryClient.getInstances(AUTHSERVICE);
		if (list != null && list.size() > 0 ) {
			list.get(0).getUri();
		}*/
	}
}
