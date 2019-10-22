package com.example.sccommon.dynamic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author qijx
 * @date 2019-10-12 15:55
 */


@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    public DynamicDataSourceAspect() {
    }

    @Before("@annotation(com.example.sccommon.util.dynamic.TargetDataSource)")
    public void changeDataSource(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class[] argClass = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();

        try {
            Method method = className.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource annotation = (TargetDataSource)method.getAnnotation(TargetDataSource.class);
                DynamicDataSourceEnum dynamicDataSourceEnum = annotation.value();
                if (DynamicDataSourceContextHolder.containsDataSource(dynamicDataSourceEnum.name())) {
                    log.debug("Use DataSource : {} > {}", dynamicDataSourceEnum.name(), joinPoint.getSignature());
                    DynamicDataSourceContextHolder.setDataSource(dynamicDataSourceEnum.name());
                }
            }
        } catch (Exception var8) {
            log.error("Exception", var8);
        }

    }

    @After("@annotation(com.example.sccommon.util.dynamic.TargetDataSource)")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSource();
    }
}
