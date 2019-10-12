/*
 * Filename OpenAppService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service;

import com.rome.openapi.backend.vo.AppEntry;
import com.rome.openapi.backend.vo.OpenApp;

import java.util.List;
import java.util.Map;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/25
 */
public interface OpenAppService {
	List<OpenApp> getAllOpenApp(Map<String, Object> map);

	void addOpenApp(OpenApp openApp);

	/**
	 * 获取app带IP白名单信息
	 * @return
	 */
	List<AppEntry> getAppWithIP();

	AppEntry get(String appId);

	List<OpenApp> list(Map<String, Object> map);

	int save(OpenApp app);

	int update(OpenApp app);

	int remove(String appId);

	int batchRemove(String[] appIds);
}
