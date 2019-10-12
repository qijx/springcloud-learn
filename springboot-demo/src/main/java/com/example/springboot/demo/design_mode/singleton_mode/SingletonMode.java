package com.example.springboot.demo.design_mode.singleton_mode;

/**
 * @author qijx
 * @date 2019-07-10 15:12
 * <p>
 * 单列模式：双检锁/双重校验锁
 * 描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
 */

public class SingletonMode {

    /**
     * volatile保证该对象对其他线程可见
     */
    private volatile static SingletonMode singletonMode;

    /**
     * 私有化构造方法
     */
    private SingletonMode() {

    }

    /**
     * 设计模式地址：https://www.runoob.com/design-pattern/filter-pattern.html
     */



    /**
     * 获取单例对象
     *
     * @return
     */
    public static SingletonMode getSingletonMode() {
        //第一次校验
        if (singletonMode == null) {
            synchronized (SingletonMode.class) {
                //第二次校验
                if (singletonMode == null) {
                    singletonMode = new SingletonMode();
                }
            }
        }
        return singletonMode;
    }


    public static <T> T getSingletonMode1(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }



}
