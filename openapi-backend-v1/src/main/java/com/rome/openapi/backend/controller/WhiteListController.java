/*
 * Filename WhiteListController.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rome.openapi.backend.common.Query;
import com.rome.openapi.backend.service.UriWhiteListService;
import com.rome.openapi.backend.util.R;
import com.rome.openapi.backend.util.page.PageUtils;
import com.rome.openapi.backend.vo.UriWhiteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 白名单管理
 *
 *
 * @since 1.0.0_2018/8/7
 */
@Controller
@RequestMapping("/whiteList")
public class WhiteListController {
	@Autowired
	private UriWhiteListService whiteListService;

	@ResponseBody
	@PostMapping("/list")
	public PageUtils list(@RequestBody Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		PageHelper.startPage(query.getPageNum(),query.getPageSize());
		List<UriWhiteList> whiteListList = whiteListService.list(query);
		PageInfo<UriWhiteList> page = new PageInfo<>(whiteListList);
		return new PageUtils(page);
	}

	@GetMapping("/add")
	String add(){
		return "whiteList/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id,Model model){
		UriWhiteList whiteList = whiteListService.get(id);
		model.addAttribute("whiteList", whiteList);
		return "whiteList/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save(@RequestBody UriWhiteList whiteList){
		if (whiteList.getAvailable() == null) {
			whiteList.setAvailable(false);
		}
		if(whiteListService.save(whiteList)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody UriWhiteList whiteList){
		if (whiteList.getAvailable() == null) {
			whiteList.setAvailable(false);
		}
		whiteListService.update(whiteList);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@DeleteMapping( "/remove")
	@ResponseBody
	public R remove(@RequestParam Integer id){
		if(whiteListService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestBody Integer[] ids){
		whiteListService.batchRemove(ids);
		return R.ok();
	}

}