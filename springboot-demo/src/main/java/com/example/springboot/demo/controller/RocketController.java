package com.example.springboot.demo.controller;

import com.example.springboot.demo.mq.rocketmq.RocketMqProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author qijx
 * @date 2019-08-06 12:48
 */
@RestController
@RequestMapping("/rocket")
@Api(description = "mq测试控制层")
public class RocketController {

    @Autowired
    private RocketMqProducer rocketMqProducer;

    @ApiOperation(value = "mq发送消息接口", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "success")
    @GetMapping("/sendMessage")
    public void sendMessage(@RequestParam("s") String s) {
        rocketMqProducer.sendMessage(s);

    }


        public static void main(String[] args) {

            System.out.println(UUID.randomUUID().toString());


        }







}
