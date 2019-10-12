/*
 * Filename OpenApiGroupServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service.impl;

import com.rome.openapi.backend.common.annotation.AuthCacheFresh;
import com.rome.openapi.backend.mapper.OpenApiGroupMapper;
import com.rome.openapi.backend.mapper.OpenApiMapper;
import com.rome.openapi.backend.service.OpenApiGroupService;
import com.rome.openapi.backend.vo.OpenApiGroup;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/24
 */

@Service
public class OpenApiGroupServiceImpl implements OpenApiGroupService {
	@Autowired
	private OpenApiGroupMapper openApiGroupMapper;
	@Autowired
	private OpenApiMapper openApiMapper;
	
	@Override
	public OpenApiGroup get(Integer id){
		return openApiGroupMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<OpenApiGroup> list(Map<String, Object> map){
		return openApiGroupMapper.list(map);
	}
	

	@Override
	public int save(OpenApiGroup apiGroup){
		if (apiGroup.getCreateTime() == null) {
			apiGroup.setCreateTime(new Date());
		}
		if (StringUtils.isBlank(apiGroup.getCreateUser())) {
			apiGroup.setCreateUser("admin");
		}
		apiGroup.setAvailable(0);//默认注册服务时不注册服务api
		return openApiGroupMapper.insertSelective(apiGroup);
	}
	
	@Override
	public int update(OpenApiGroup apiGroup){
		apiGroup.setCreateTime(new Date());
		return openApiGroupMapper.updateByPrimaryKeySelective(apiGroup);
	}

	@AuthCacheFresh
	@Transactional
	@Override
	public int remove(Integer id){
		this.openApiMapper.removeByGroupId(id);
		return openApiGroupMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return openApiGroupMapper.batchRemove(ids);
	}

	@Override
	public OpenApiGroup selectByService(String service) {
		return this.openApiGroupMapper.selectByService(service);
	}

	@Override
	public void register(String service) {
		OpenApiGroup openApiGroup = this.openApiGroupMapper.selectByService(service);
		//先判断是否已存在该servive，如果不存在则注册服务
		if (openApiGroup == null) {
			openApiGroup = new OpenApiGroup();
			openApiGroup.setService(service);
			openApiGroup.setGroup("1");
			this.save(openApiGroup);
		}else if(openApiGroup.getDeleted()==1){
			openApiGroup.setDeleted(0);
			openApiGroup.setAvailable(0);
			this.update(openApiGroup);
		}
	}

	@Override
	public List<OpenApiGroup> listAll() {
		return this.openApiGroupMapper.listAll();
	}

}
