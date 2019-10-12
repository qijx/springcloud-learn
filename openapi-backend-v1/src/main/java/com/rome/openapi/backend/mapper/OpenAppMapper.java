package com.rome.openapi.backend.mapper;

import com.rome.openapi.backend.vo.AppEntry;
import com.rome.openapi.backend.vo.OpenApp;

import java.util.List;
import java.util.Map;

public interface OpenAppMapper {
    int deleteByPrimaryKey(String appId);

    int insert(OpenApp record);

    int insertSelective(OpenApp record);

    OpenApp selectByPrimaryKey(String appId);

    AppEntry selectAppWithIPByAppid(String appId);

    int updateByPrimaryKeySelective(OpenApp record);

    int updateByPrimaryKey(OpenApp record);

    List<OpenApp> selectAll(Map<String, Object> map);

    List<AppEntry> selectAppWithIP();

    int batchRemove(String[] appIds);
}