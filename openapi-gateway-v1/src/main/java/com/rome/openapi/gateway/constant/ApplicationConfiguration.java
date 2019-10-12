/*
 * Filename ApplicationConfiguration.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.constant;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/1
 */
@Configuration
public class ApplicationConfiguration {
	/**
	 * 使用正则规则去路由，如果当正则不匹配时，使用默认的serviceId路由规则
	 * regexmapper  regular-expression serviceId
	 * eg:myusers-v1 to myusers/v1/**
	 * @return
	 */
//	@Bean
	public PatternServiceRouteMapper serviceRouteMapper() {
		return new PatternServiceRouteMapper(
				"(?<name>^.+)-(?<version>v\\d+(\\.\\d+)*$)",//(?<name>^.+)-(?<version>v.+$)
				"${name}/${version}");
	}

	/**
	 * 重定向时重写Location header
	 * @return
	 */
	@Bean
	public LocationRewriteFilter locationRewriteFilter() {
		return new LocationRewriteFilter();
	}
}
