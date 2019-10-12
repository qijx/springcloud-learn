/*
 * Filename UriWhiteListService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service;

import com.rome.openapi.backend.vo.UriWhiteList;

import java.util.List;
import java.util.Map;

/**
 * URL白名单服务
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
public interface UriWhiteListService {

	/**
	 * 获取所有有效的url白名单
	 * @return
	 */
	List<UriWhiteList> getAllList();

	/**
	 * 获取有效的URI列表
	 */
	List<String> getAllListOnlyUri();

	UriWhiteList get(Integer id);

	List<UriWhiteList> list(Map<String, Object> map);

	int save(UriWhiteList whiteList);

	int update(UriWhiteList whiteList);

	int remove(Integer id);

	int batchRemove(Integer[] ids);
}
