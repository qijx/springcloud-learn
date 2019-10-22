package com.example.sccommon.dynamic;

import java.lang.annotation.*;

/**
 * @author qijx
 * @date 2019-10-12 15:58
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    DynamicDataSourceEnum value() default DynamicDataSourceEnum.WRITE;
}
