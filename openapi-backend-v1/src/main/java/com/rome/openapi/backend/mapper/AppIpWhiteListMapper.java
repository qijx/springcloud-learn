package com.rome.openapi.backend.mapper;

import com.rome.openapi.backend.vo.AppIpWhiteList;

public interface AppIpWhiteListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppIpWhiteList record);

    int insertSelective(AppIpWhiteList record);

    AppIpWhiteList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppIpWhiteList record);

    int updateByPrimaryKey(AppIpWhiteList record);

    int updateAppIpWhiteList (AppIpWhiteList record);

    AppIpWhiteList selectByAppid(String appid);
}