package com.example.springboot.demo.design_mode.proxy_mode.dynamic_proxy.cglib_proxy;

/**
 * @author qijx
 * @date 2019-07-16 16:16
 *
 * 没有实现接口，需要CGlib动态代理的目标类
 */

public class UserServiceImpl {

    /**
     * 可以被代理
     */
    public void add() {
        System.out.println("添加数据");
    }

    /**
     *  final 方法不会被生成的字类覆盖
     *
     */
    public final void query() {
        System.out.println("查询数据");
    }

    /**
     * private 方法不会被生成的字类覆盖
     */
    private void delete() {
        System.out.println("删除数据");
    }


}
