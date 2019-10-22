package com.example.scqijx.utils.aop;

/**
 * @author qijx
 * @date 2019-10-21 16:56
 */

import java.lang.annotation.*;


/**
 * 注解形式的切面
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface TestAnnotation {

}
