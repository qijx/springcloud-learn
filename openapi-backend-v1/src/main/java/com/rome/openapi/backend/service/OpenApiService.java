/*
 * Filename OpenApiService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service;

import com.rome.openapi.backend.util.Tree;
import com.rome.openapi.backend.vo.ApiEntry;
import com.rome.openapi.backend.vo.ApiGroup;
import com.rome.openapi.backend.vo.OpenApi;
import com.rome.openapi.backend.vo.OpenApiGroupEntry;

import java.util.List;
import java.util.Map;

/**
 * API服务
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
public interface OpenApiService {

	/**
	 * 获取带app权限的api
	 * @return
	 */
	List<ApiEntry> getApiWithApp();

	OpenApi get(Integer id);

	List<OpenApiGroupEntry> list(Map<String, Object> map);


	int save(OpenApi api);

	int update(OpenApi api);

	int remove(Integer id);

	int batchRemove(Integer[] ids);

	List<OpenApi> selectByGroupId(Integer groupId);

	Tree<ApiGroup> getTree();

	void registerAPI(Integer serviceId);
}
