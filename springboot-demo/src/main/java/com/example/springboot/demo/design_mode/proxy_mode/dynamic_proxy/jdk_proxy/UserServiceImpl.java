package com.example.springboot.demo.design_mode.proxy_mode.dynamic_proxy.jdk_proxy;

/**
 * @author qijx
 * @date 2019-07-16 15:05
 */

public class UserServiceImpl implements UserService{


    @Override
    public String addUser(String username) {
        StringBuilder sb=new StringBuilder("保存接收到的结果为：");
        return sb.append(username).toString();
    }


}
