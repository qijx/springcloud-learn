package com.rome.openapi.backend.mapper;

import com.rome.openapi.backend.vo.AppApiEntry;
import com.rome.openapi.backend.vo.AppSelectedApi;

import java.util.List;

public interface AppApiEntryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppApiEntry record);

    int insertSelective(AppApiEntry record);

    int batchInsert(List<AppApiEntry> appApiEntries);

    AppApiEntry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppApiEntry record);

    int updateByPrimaryKey(AppApiEntry record);

    List<AppApiEntry> queryAllAppApis();

    List<AppSelectedApi> queryAppApis(String appid);

    void deleteAppApi(AppApiEntry appApiEntry);

    void batchDelAppApi(List<String> list);

    int existsAppApi(AppApiEntry appApiEntry);

    void updateAppApi(AppApiEntry appApiEntry);

}