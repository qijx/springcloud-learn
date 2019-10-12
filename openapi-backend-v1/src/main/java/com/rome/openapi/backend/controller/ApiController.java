package com.rome.openapi.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rome.openapi.backend.common.Query;
import com.rome.openapi.backend.service.OpenApiService;
import com.rome.openapi.backend.util.R;
import com.rome.openapi.backend.util.Tree;
import com.rome.openapi.backend.util.page.PageUtils;
import com.rome.openapi.backend.vo.OpenApi;
import com.rome.openapi.backend.vo.OpenApiGroupEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * API管理
 *
 * api接口增删改查操作
 * @since 1.0.0_2018/8/7
 */

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private OpenApiService apiService;

    /**
     * 接口api查询
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public PageUtils list(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<OpenApiGroupEntry> apiList = apiService.list(query);
        PageInfo<OpenApiGroupEntry> page = new PageInfo<>(apiList);
        return new PageUtils(page);
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree tree() {
        return apiService.getTree();
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        OpenApi api = apiService.get(id);
        model.addAttribute("api", api);
        return "api/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(OpenApi api) {
        if (api.getAvailable() == null) {
            api.setAvailable(false);
        }
        if (apiService.save(api) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(@RequestBody OpenApi api) {
        if (api.getAvailable() == null) {
            api.setAvailable(false);
        }
        apiService.update(api);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(@RequestParam Integer id) {
        if (apiService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestBody Integer[] ids) {
        apiService.batchRemove(ids);
        return R.ok();
    }

}
