/*
 * Filename OpenApiGroupService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service;

import com.rome.openapi.backend.vo.OpenApiGroup;

import java.util.List;
import java.util.Map;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/24
 */
public interface OpenApiGroupService {
	
	OpenApiGroup get(Integer id);
	
	List<OpenApiGroup> list(Map<String, Object> map);
	

	int save(OpenApiGroup apiGroup);
	
	int update(OpenApiGroup apiGroup);


	/**
	 * 删除service
	 * @param id
	 */
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	OpenApiGroup selectByService(String service);

	void register(String service);

	List<OpenApiGroup> listAll();
}
