package com.rome.openapi.backend.service;

import com.rome.openapi.backend.vo.AppApiEntry;
import com.rome.openapi.backend.vo.AppSelectedApi;
import com.rome.openapi.backend.vo.BatchAppApiModel;

import java.util.List;

public interface AppApiService {

    public List<AppApiEntry> queryAppAPis();

    public List<AppSelectedApi> queryAppApis(String appid);

    public void delAppApi(Integer id);

    public void batchDelAppApis(String ids);

    public void addAppApi(AppApiEntry appApiEntry);

    public void addAppApiBatch(BatchAppApiModel model);

    public void updateAppApi(AppApiEntry appApiEntry);
}
