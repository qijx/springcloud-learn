package com.example.springboot.demo.design_mode.proxy_mode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qijx
 * @date 2019-07-16 14:43
 * 静态代理类Proxy
 */
@Slf4j
public class UserServiceProxy implements UserService{


    private UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String addUser(String username) {
        log.info("在目标方法之前，执行代理类proxy的方法");
        String s = userService.addUser(username);
        log.info("目标方法执行之后可以处理的逻辑");
        return s;
    }
}
