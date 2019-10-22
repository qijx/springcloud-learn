package com.example.sccommon.dynamic;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author qijx
 * @date 2019-10-12 15:58
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({DynamicDataSourceRegister.class})
public @interface EnableDynamicDataSource {
}
