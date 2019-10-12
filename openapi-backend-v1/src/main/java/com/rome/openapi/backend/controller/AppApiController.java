package com.rome.openapi.backend.controller;

import com.rome.openapi.backend.service.AppApiService;
import com.rome.openapi.backend.util.R;
import com.rome.openapi.backend.vo.AppApiEntry;
import com.rome.openapi.backend.vo.AppSelectedApi;
import com.rome.openapi.backend.vo.BatchAppApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP(商家)-API(服务)管理
 *
 * 商家与api接口关联关系操作
 */
@RestController
@RequestMapping(value = "/appapi")
public class AppApiController {

    private static Logger logger = LoggerFactory.getLogger(AppApiController.class);

    @Autowired
    AppApiService appApiService;


    @GetMapping
    public String appapi(){
        return "appapi/appapi";
    }

    @GetMapping(value = "/add")
    public String addAppApi(){
        return "appapi/add";
    }

    @GetMapping(value = "/{appid}")
    @ResponseBody
    public Object appapi(@PathVariable("appid") String appid){

        List<AppSelectedApi> list = appApiService.queryAppApis(appid);

        return list;
    }

    @RequestMapping(value = "/{appApiId}", method = RequestMethod.DELETE)
    @ResponseBody
    public R del(@PathVariable("appApiId") Integer id){
        try {
            appApiService.delAppApi(id);
        }catch (Exception e){
            logger.error("删除异常",e);
            return R.error(-1,"删除失败");
        }
        return R.ok();
    }

    @RequestMapping(value = "/batch/{appApiIds}", method = RequestMethod.DELETE)
    @ResponseBody
    public R batchDel(@PathVariable("appApiIds") String appApiIds){
        try {
            appApiService.batchDelAppApis(appApiIds);
        }catch (Exception e){
            logger.error("删除异常",e);
            return R.error(-1,"删除失败");
        }
        return R.ok();
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R add(@RequestBody AppApiEntry entry){
        try {
            appApiService.addAppApi(entry);
        }catch (Exception e){
            logger.error("添加异常",e);
            return R.error(-1,"添加失败");
        }
        return R.ok();
    }

    @RequestMapping(value = "/batchSave",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R batchSave(@RequestBody BatchAppApiModel model){
        try {
            appApiService.addAppApiBatch(model);
        }catch (Exception e){
            logger.error("添加异常",e);
            return R.error(-1,"添加失败");
        }
        return R.ok();
    }

    @RequestMapping(value = "/update",method = RequestMethod.PATCH,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public R update(@RequestBody AppApiEntry entry){

        try {
            appApiService.updateAppApi(entry);
        }catch (Exception e){
            logger.error("更新异常",e);
            return R.error(-1,"更新失败");
        }

        return R.ok();
    }


}
