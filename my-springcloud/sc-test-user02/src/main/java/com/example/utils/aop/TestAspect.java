package com.example.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author qijx
 * @date 2019-10-21 16:58
 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class TestAspect {


    /**
     *
     * @param joinPoint
     */
    @Before("@annotation(com.example.utils.aop.TestAnnotation)")
    public void testBefore(JoinPoint joinPoint) {
       log.info("方法执行之前进入该切面：{}",joinPoint);
    }

    /**
     * 环绕型切面
     * @param joinPoint
     */
    @Around("@annotation(com.example.utils.aop.TestAnnotation)")
    public void testAround(JoinPoint joinPoint) {
       log.info("环绕行切面切面：{}",joinPoint);
    }

    /**
     *
     * @param joinPoint
     */
    @After("@annotation(com.example.utils.aop.TestAnnotation)")
    public void testAfter(JoinPoint joinPoint) {
       log.info("方法执行之后进入该切面：{}",joinPoint);
    }



}
