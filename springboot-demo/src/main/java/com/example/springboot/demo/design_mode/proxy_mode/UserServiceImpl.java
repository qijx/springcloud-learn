package com.example.springboot.demo.design_mode.proxy_mode;


/**
 * @author qijx
 * @date 2019-07-16 14:42
 *
 * 被代理类
 */

public class UserServiceImpl implements UserService{


    @Override
    public String addUser(String username) {
        StringBuilder sb=new StringBuilder("保存接收到的结果为：");
        return sb.append(username).toString();
    }
}
