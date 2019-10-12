package com.example.springboot.demo.design_mode.proxy_mode.dynamic_proxy.jdk_proxy;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.lang.reflect.Proxy;

/**
 * @author qijx
 * @date 2019-07-16 14:58
 */
@Slf4j
public class JdkProxyClient {


    /**
     * 设计模式地址：https://www.runoob.com/design-pattern/filter-pattern.html
     */


    /**
     * 1.JDK中的动态代理是通过反射类Proxy以及InvocationHandler回调接口实现的，
     * 但是，JDK中所要进行动态代理的类必须要实现一个接口，
     * 也就是说只能对该类所实现接口中定义的方法进行代理，
     * 这在实际编程中具有一定的局限性，而且使用反射的效率也并不是很高。
     *2.而CGLib原理是动态生成被代理类的子类。所以不需要代理类实现接口也可以进行代理
     *
     *
     * jdk动态代理的实现步骤需要用到一个Proxy类,和一个接口
     * 1.java.lang.reflect.Proxy:用到的代理类和对象
     * 2.java.lang.reflect.InvocationHandler代理类需要实现对的invoke接口
     * 对真实角色的代理访问。
     * 每次通过 Proxy 生成的代理类对象都要指定对应的处理器对象。（该处理器对象是程序员自己实现的）
     */


        public static void main(String[] args) {
            //真实对象
            UserService userService = new UserServiceImpl();
            MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);

            //代理对象
            /**
             * 1.第一个参数是指定代理类的类加载器（我们传入当前测试类的类加载器）
             * 2.第二个参数是代理类需要实现的接口（我们传入被代理类实现的接口，这样生成的代理类和被代理类就实现了相同的接口）
             * 3.第三个参数是invocation handler，用来处理方法的调用。这里传入我们自己实现的handler
             */
            UserService proxy= (UserService) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                    userService.getClass().getInterfaces(),
                    myInvocationHandler);
            //执行代理类的方法
            String s = proxy.addUser("小明");
            log.info("得到返回结果为：{}",s);
        }


}
