package com.example.springboot.demo.design_mode.proxy_mode.dynamic_proxy.cglib_proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author qijx
 * @date 2019-07-16 16:21
 */

public class CglibInterceptor implements MethodInterceptor {

    /**
     * 目标对象
     */
    private Object target;

    public CglibInterceptor(Object target) {
        this.target = target;
    }


    /**
     * 重写方法拦截在方法前和方法后加入业务
     * 上面的Object target为目标对象（该方法中的第一个参数object跟jdk代理一样，在这里暂无作用）
     * Method method为目标方法
     * Object[] params 为参数，
     * MethodProxy proxy CGlib方法代理对象
     */
    @Override
    public Object intercept(Object o, Method method, Object[] params,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("调用前执行方法。。。。。");
        /**
         * 这两个方法执行效果是一样的
         */
        Object invoke = method.invoke(target, params);
        Object invoke1 = methodProxy.invoke(target, params);
        System.out.println("调用后执行方法。。。。。");
        return invoke;
    }


    /**
     * 这里Enhancer类是CGLib中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展，以后会经常看到它。
     *
     * 首先将被代理类TargetObject设置成父类，然后设置拦截器TargetInterceptor，
     * 最后执行enhancer.create()动态生成一个代理类，并从Object强制转型成父类型TargetObject。
     *最后，在代理类上调用方法。
     * @param target
     * @return
     */
    public static Object getCglibProxy(Object target){
        Enhancer enhancer = new Enhancer();
        // 设置需要代理的对象
        enhancer.setSuperclass(target.getClass());
        // 设置代理人
        enhancer.setCallback(new CglibInterceptor(target));
        return enhancer.create();
    }




}
