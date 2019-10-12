/*
 * Filename OpenAppServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service.impl;

import com.rome.openapi.backend.common.annotation.AuthCacheFresh;
import com.rome.openapi.backend.util.constant.IDService;
import com.rome.openapi.backend.mapper.AppIpWhiteListMapper;
import com.rome.openapi.backend.mapper.OpenAppMapper;
import com.rome.openapi.backend.service.OpenAppService;
import com.rome.openapi.backend.util.id.IdUtil;
import com.rome.openapi.backend.vo.AppEntry;
import com.rome.openapi.backend.vo.AppIpWhiteList;
import com.rome.openapi.backend.vo.OpenApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/25
 */
@Service
public class OpenAppServiceImpl implements OpenAppService {

	@Autowired
	OpenAppMapper openAppMapper;

	@Autowired
	AppIpWhiteListMapper ipWhiteListMapper;

	@Override
	public List<OpenApp> getAllOpenApp(Map<String, Object> map) {
		return openAppMapper.selectAll(map);
	}

	@Override
	public void addOpenApp(OpenApp openApp) {
		this.save(openApp);
	}

	@Override
	public List<AppEntry> getAppWithIP() {
		return openAppMapper.selectAppWithIP();
	}

	@Override
	public AppEntry get(String appId) {
		return openAppMapper.selectAppWithIPByAppid(appId);
	}

	@Override
	public List<OpenApp> list(Map<String, Object> map) {
		return openAppMapper.selectAll(map);
	}

	@Transactional(rollbackFor = Exception.class)
	@AuthCacheFresh
	@Override
	public int save(OpenApp app) {
		Date now = new Date();
		app.setAppId(IDService.getAppId());
		app.setCreateTime(now);
		app.setCreateUser("admin");
		app.setUpdateTime(now);
		app.setUpdateUser("admin");
		app.setSecretKey(IdUtil.getIdUpperCaseLeft("SecretKey-",32));

		if (app.getHasWhiteList()){
			saveAppIpWhiteList(app.getAppId(),app.getIptables());
		}

		return openAppMapper.insertSelective(app);
	}

	@Transactional(rollbackFor = Exception.class)
	@AuthCacheFresh
	@Override
	public int update(OpenApp app) {
		Date date = new Date();
		app.setUpdateTime(date);

		updateAppIpWhiteList(app.getAppId(),app.getIptables());

		return openAppMapper.updateByPrimaryKeySelective(app);
	}

	private void saveAppIpWhiteList(String appid,String iptables){
		AppIpWhiteList ipWhiteList = new AppIpWhiteList();
		ipWhiteList.setAppId(appid);
		ipWhiteList.setIp(iptables);
		ipWhiteList.setAvailable(true);
		ipWhiteList.setDeleted(false);
		ipWhiteList.setCreateTime(new Date());
		ipWhiteList.setCreateUser("admin");

		ipWhiteListMapper.insert(ipWhiteList);
	}

	private void updateAppIpWhiteList(String appid,String iptables){

		AppIpWhiteList ips = ipWhiteListMapper.selectByAppid(appid);
		if (null == ips){
			saveAppIpWhiteList(appid,iptables);
		}else {
		    ips.setIp(iptables);
            ips.setUpdateTime(new Date());
            ips.setUpdateUser("admin");

		    ipWhiteListMapper.updateByPrimaryKey(ips);
        }

	}

	@AuthCacheFresh
	@Override
	public int remove(String appId) {
		return openAppMapper.deleteByPrimaryKey(appId);
	}

	@AuthCacheFresh
	@Override
	public int batchRemove(String[] appIds) {
		return openAppMapper.batchRemove(appIds);
	}


}
