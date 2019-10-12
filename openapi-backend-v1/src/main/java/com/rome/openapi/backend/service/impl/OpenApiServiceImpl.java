/*
 * Filename OpenApiServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service.impl;

import com.rome.openapi.backend.common.annotation.AuthCacheFresh;
import com.rome.openapi.backend.mapper.OpenApiMapper;
import com.rome.openapi.backend.service.OpenApiService;
import com.rome.openapi.backend.util.BuildTree;
import com.rome.openapi.backend.util.Tree;
import com.rome.openapi.backend.vo.ApiEntry;
import com.rome.openapi.backend.vo.ApiGroup;
import com.rome.openapi.backend.vo.OpenApi;
import com.rome.openapi.backend.vo.OpenApiGroupEntry;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
@Service
public class OpenApiServiceImpl implements OpenApiService {
	@Autowired
	OpenApiMapper openApiMapper;
	@Override
	public List<ApiEntry> getApiWithApp() {
		return openApiMapper.selectWithApp();
	}

	@Override
	public OpenApi get(Integer id) {
		return openApiMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<OpenApiGroupEntry> list(Map<String, Object> map) {
		return openApiMapper.list(map);
	}

	@AuthCacheFresh
	@Override
	public int save(OpenApi api) {
		Date now = new Date();
		if (StringUtils.isBlank(api.getVersion())) {
			api.setVersion("1.0.0");
		}
		if (StringUtils.isBlank(api.getCreateUser())) {
			api.setCreateUser("admin");
		}
		if (api.getCreateTime()==null) {
			api.setCreateTime(now);
		}
		api.setUpdateUser("admin");
		api.setUpdateTime(now);
		return openApiMapper.insertSelective(api);
	}

	@AuthCacheFresh
	@Override
	public int update(OpenApi api) {
		Date now = new Date();
		api.setUpdateUser("admin");
		api.setUpdateTime(now);
		return openApiMapper.updateByPrimaryKeySelective(api);
	}

	@Override
	public int remove(Integer id) {
		return openApiMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int batchRemove(Integer[] ids) {
		return openApiMapper.batchRemove(ids);
	}

	@Override
	public List<OpenApi> selectByGroupId(Integer groupId) {
		return this.openApiMapper.selectByGroupId(groupId);
	}

	@Override
	public Tree<ApiGroup> getTree() {
		List<ApiGroup> list = this.openApiMapper.groupList();
		List<Tree<ApiGroup>> trees = new ArrayList<>();
		for (ApiGroup apiGroup : list) {
			Tree<ApiGroup> tree = new Tree<ApiGroup>();
			tree.setId(String.valueOf(apiGroup.getId()));
			tree.setParentId("0");
			tree.setText(apiGroup.getService());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "group");
			tree.setState(state);
			trees.add(tree);
		}

		return BuildTree.build(trees);
	}

	@Override
	public void registerAPI(Integer serviceId) {

	}

}
