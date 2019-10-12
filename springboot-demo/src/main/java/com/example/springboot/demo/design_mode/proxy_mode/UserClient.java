package com.example.springboot.demo.design_mode.proxy_mode;

/**
 * @author qijx
 * @date 2019-07-16 14:50
 */

public class UserClient {

    /**
     * 设计模式地址：https://www.runoob.com/design-pattern/filter-pattern.html
     */
        public static void main(String[] args) {
            /**
             * 被代理类
             */
            UserService target = new UserServiceImpl();
            /**
             * 代理类
             */
            UserServiceProxy proxy = new UserServiceProxy(target);
            String s = proxy.addUser("小明");
            System.out.println(s);

        }




}
