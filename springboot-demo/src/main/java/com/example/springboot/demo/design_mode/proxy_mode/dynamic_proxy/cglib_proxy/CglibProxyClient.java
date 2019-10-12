package com.example.springboot.demo.design_mode.proxy_mode.dynamic_proxy.cglib_proxy;

/**
 * @author qijx
 * @date 2019-07-16 16:14
 */

public class CglibProxyClient {


    /**
     * 1.使用CGLib实现动态代理，完全不受代理类必须实现接口的限制，
     * 而且CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类，
     * 比使用Java反射效率要高。唯一需要注意的是，
     * CGLib不能对声明为final和private的方法进行代理，因为CGLib原理是动态生成被代理类的子类。
     *
     * 2.使用cglib动态代理只需要实现一步，定义一个拦截器
     * 定义一个拦截器。在调用目标方法时，CGLib会回调MethodInterceptor接口方法拦截，
     * 来实现你自己的代理逻辑，类似于JDK中的InvocationHandler接口。
     */

    public static void main(String[] args) {
        UserServiceImpl cglibInterceptor =(UserServiceImpl)CglibInterceptor.getCglibProxy(new UserServiceImpl());
        cglibInterceptor.add();
        cglibInterceptor.query();
    }

}
