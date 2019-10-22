package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author qijx
 * @date 2019-10-21 13:59
 */
@Slf4j
@RestController
@RequestMapping("/qijx")
@Api(description = "测试项目控制层")
public class TestController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success", response = String.class)
    @GetMapping("/user01")
    public String getActivityList(Map<String, Object> module){
        User user = userService.getUserByName("user01");
        module.put("user", user);
        return "my name is user01";
    }


}
