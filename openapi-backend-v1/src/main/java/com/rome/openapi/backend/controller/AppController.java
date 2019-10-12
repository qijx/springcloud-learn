/*
 * Filename OpenAppController.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rome.arch.core.clientobject.Response;
import com.rome.openapi.backend.common.Query;
import com.rome.openapi.backend.util.constant.IDService;
import com.rome.openapi.backend.service.OpenAppService;
import com.rome.openapi.backend.util.R;
import com.rome.openapi.backend.util.id.IdUtil;
import com.rome.openapi.backend.util.page.PageUtils;
import com.rome.openapi.backend.vo.AppEntry;
import com.rome.openapi.backend.vo.OpenApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * APP(商家)管理
 *
 * 商家第三方controller
 * @since 1.0.0_2018/7/25
 */
@Controller
@RequestMapping("/openApp")
public class AppController {
	Logger log = LoggerFactory.getLogger(AppController.class);


	@Autowired
	OpenAppService openAppService;

	@GetMapping()
	String openApp() {
		return "openApp/app";
	}


	/**
	 * 生成clientId和Secret
	 * @param args
	 */
	public static void main(String[] args) {
		String clientId = IDService.getAppId();
		System.out.println(clientId);
		String secret = IdUtil.getIdUpperCaseLeft("SecretKey-", 32);
		System.out.println(secret);

	}



	@RequestMapping(value = "/openAppList",method = RequestMethod.GET)
	public ModelAndView getAllOpenApp(@RequestParam Map<String, Object> params) {
		ModelAndView modelAndView = new ModelAndView();
		Query query = new Query(params);
		PageHelper.startPage(query);
		log.info("----------getAllOpenApp----------");
		List<OpenApp> openAppList = openAppService.getAllOpenApp(params);
		PageInfo<OpenApp> page = new PageInfo<>(openAppList);
		modelAndView.addObject("openAppList", openAppList);
		modelAndView.addObject("page", page);
		modelAndView.setViewName("openApp/openAppList");
		return modelAndView;
	}

	@ResponseBody
	@PostMapping("/list")
	public PageUtils list(@RequestBody Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		PageHelper.startPage(query.getPageNum(),query.getPageSize());
		List<OpenApp> openAppList = openAppService.list(params);
		PageInfo<OpenApp> page = new PageInfo<>(openAppList);
		return new PageUtils(page);
	}

	@RequestMapping(value = "/openAppInfo",method = RequestMethod.POST)
	public String openAppInfoAdd(HttpServletRequest request, OpenApp openApp) {
		log.info("add openApp...");
		openAppService.addOpenApp(openApp);
		log.info("add openApi end...");
		return "redirect:/openApp/openAppList";
	}

	@GetMapping("/add")
	public ModelAndView add(){

		ModelAndView mv = new ModelAndView();
		mv.setViewName("openApp/add");

		List<String> list = new ArrayList<>();
		list.add("RSA");
		list.add("DES");
		list.add("DEFAULT");

		mv.addObject("secretType",list);
		return mv;
	}

//	@GetMapping("/edit/{appId}")
//	String edit(@PathVariable("appId") String appId,Model model){
//
//		List<String> list = new ArrayList<>();
//		list.add("RSA");
//		list.add("DES");
//		list.add("DEFAULT");
//
//		AppEntry app = openAppService.get(appId);
//		model.addAttribute("app", app);
//		model.addAttribute("secretType", list);
//		return "openApp/edit";
//	}

	@GetMapping("/edit/{appId}")
	@ResponseBody
	public Response<AppEntry> edit(@PathVariable("appId") String appId){
		AppEntry app = openAppService.get(appId);
		return Response.builderSuccess(app);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save(@RequestBody OpenApp app){
		if (app.getAppType() == null) {
			app.setAppType(false);
		}
		if (app.getAvailable() == null) {
			app.setAvailable(false);
		}
		if (app.getHasWhiteList() == null) {
			app.setHasWhiteList(false);
		}
		if(openAppService.save(app)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody OpenApp app){
		if (app.getAppType() == null) {
			app.setAppType(false);
		}
		if (app.getAvailable() == null) {
			app.setAvailable(false);
		}
		if (app.getHasWhiteList() == null) {
			app.setHasWhiteList(false);
		}
		openAppService.update(app);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove(@RequestParam String appId){
		if(openAppService.remove(appId)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestBody String[] appIds){
		openAppService.batchRemove(appIds);
		return R.ok();
	}

	@RequestMapping(value = "/openAppInfoAdd",method = RequestMethod.GET)
	public String openAppInfoAddTurn() {
		return "openApp/openAppAdd";
	}

	@RequestMapping(value = "/openTest",method = RequestMethod.GET)
	public String openTest(){
		return "test/openTest";
	}
}
