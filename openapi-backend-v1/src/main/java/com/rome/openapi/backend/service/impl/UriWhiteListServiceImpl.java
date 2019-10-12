/*
 * Filename UriWhiteListServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service.impl;

import com.rome.openapi.backend.common.annotation.AuthCacheFresh;
import com.rome.openapi.backend.mapper.UriWhiteListMapper;
import com.rome.openapi.backend.service.UriWhiteListService;
import com.rome.openapi.backend.vo.UriWhiteList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
@Service
public class UriWhiteListServiceImpl implements UriWhiteListService {
	private final Logger log = LoggerFactory.getLogger(UriWhiteListServiceImpl.class);

	@Autowired
	UriWhiteListMapper uriWhiteListMapper;
	@Override
	public List<UriWhiteList> getAllList() {
		log.info("get UriWhiteList start...");
		List<UriWhiteList> list = uriWhiteListMapper.selectAll();
		log.info("get UriWhiteList end...");
		return list;
	}

	@Override
	public List<String> getAllListOnlyUri() {
		log.info("get AllListOnlyUri start...");
		List<String> list = uriWhiteListMapper.selectAllListOnlyUri();
		log.info("get AllListOnlyUri end...");
		return list;
	}

	@Override
	public UriWhiteList get(Integer id) {
		return uriWhiteListMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UriWhiteList> list(Map<String, Object> map) {
		return uriWhiteListMapper.list();
	}

	@AuthCacheFresh
	@Override
	public int save(UriWhiteList whiteList) {
		Date now = new Date();
		whiteList.setCreateUser("admin");
		whiteList.setCreateTime(now);
		return uriWhiteListMapper.insertSelective(whiteList);
	}

	@AuthCacheFresh
	@Override
	public int update(UriWhiteList whiteList) {
		return uriWhiteListMapper.updateByPrimaryKeySelective(whiteList);
	}

	@AuthCacheFresh
	@Override
	public int remove(Integer id) {
		return uriWhiteListMapper.deleteByPrimaryKey(id);
	}

	@AuthCacheFresh
	@Override
	public int batchRemove(Integer[] ids) {
		return uriWhiteListMapper.batchRemove(ids);
	}
}
