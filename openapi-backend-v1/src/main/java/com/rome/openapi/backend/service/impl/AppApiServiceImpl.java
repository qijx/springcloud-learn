package com.rome.openapi.backend.service.impl;

import com.rome.openapi.backend.common.annotation.AuthCacheFresh;
import com.rome.openapi.backend.mapper.AppApiEntryMapper;
import com.rome.openapi.backend.service.AppApiService;
import com.rome.openapi.backend.vo.AppApiEntry;
import com.rome.openapi.backend.vo.AppSelectedApi;
import com.rome.openapi.backend.vo.BatchAppApiModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AppApiServiceImpl implements AppApiService {

    @Resource
    private AppApiEntryMapper mapper;

    @Override
    public List<AppApiEntry> queryAppAPis() {

        List<AppApiEntry> list = mapper.queryAllAppApis();

        return list;
    }

    @Override
    public List<AppSelectedApi> queryAppApis(String appid) {
        List<AppSelectedApi> list = mapper.queryAppApis(appid);
        return list;
    }

    @AuthCacheFresh
    @Override
    public void delAppApi(Integer id) {
        AppApiEntry entry = new AppApiEntry();
        entry.setUpdateTime(new Date());
        entry.setUpdateUser("admin");
        entry.setId(id);

        mapper.deleteAppApi(entry);
    }

    @AuthCacheFresh
    @Override
    public void batchDelAppApis(String ids) {

        String[] idArr = ids.split(",");

        List<String> list = Arrays.asList(idArr);

        mapper.batchDelAppApi(list);
    }

    @AuthCacheFresh
    @Override
    public void addAppApi(AppApiEntry appApiEntry) {

        int i = mapper.existsAppApi(appApiEntry);

        if (0 == i){
            AppApiEntry db = new AppApiEntry();
            db.setAppId(appApiEntry.getAppId());
            db.setApiId(appApiEntry.getApiId());
            db.setSelectField(appApiEntry.getSelectField());
            db.setCreateTime(new Date());
            db.setCreateUser("admin");
            db.setAvailable(true);
            db.setDeleted(false);

            mapper.insert(db);
        }

    }

    @AuthCacheFresh
    @Override
    public void addAppApiBatch(BatchAppApiModel model) {

        String apiIds = model.getApiIds();
        String[] spiIdArr = apiIds.split(",");

        List<AppApiEntry> appapis = new ArrayList<>();

        for (String apiId : spiIdArr){

            AppApiEntry entry = new AppApiEntry();
            entry.setAppId(model.getAppId());
            entry.setApiId(Integer.valueOf(apiId));

            int i = mapper.existsAppApi(entry);

            if (i > 0){
                continue;
            }

            entry.setSelectField("");
            entry.setCreateTime(new Date());
            entry.setCreateUser("admin");
            entry.setAvailable(true);
            entry.setDeleted(false);

            appapis.add(entry);
        }

        if (null != appapis && appapis.size()>0){
            mapper.batchInsert(appapis);
        }

    }

    @AuthCacheFresh
    @Override
    public void updateAppApi(AppApiEntry appApiEntry) {

        AppApiEntry db = new AppApiEntry();

        db.setId(appApiEntry.getId());
        db.setSelectField(appApiEntry.getSelectField());
        db.setUpdateTime(new Date());
        db.setUpdateUser("updateAdmin");

        mapper.updateAppApi(db);
    }


}
