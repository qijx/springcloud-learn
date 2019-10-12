package com.example.springboot.demo.design_mode.proxy_mode.dynamic_proxy.jdk_proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qijx
 * @date 2019-07-16 15:02
 *
 *
 * 实现InvocationHandler对象
 *
 */
@Slf4j
public class MyInvocationHandler implements InvocationHandler {

    /**
     * 因为需要处理真实角色，所以要把真实角色传进来
     */
    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }


    /**
     *
     * @param proxy    代理类
     * @param method    正在调用的方法
     * @param args      方法的参数
     * @return
     * @throws Throwable
     *
     * 在调用真实对象方法之前会先进入这个方法执行
     *
     * 该invoke方法的第一个参数proxy在这里没用不用考虑
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("代理类开始执行，接收到的参数为：{}",args);
        log.info("打印该invoke方法的第一个参数proxy是个什么鬼：{}",proxy.getClass().getName());
        //第一个参数为代理的真实对象(千万不要写该方法的第一个参数proxy不然会报错)，args为参数
        Object invoke = method.invoke(object, args);
        log.info("代理执行结束，接受到的返回值为：{}",invoke);

        return invoke;
    }
}
