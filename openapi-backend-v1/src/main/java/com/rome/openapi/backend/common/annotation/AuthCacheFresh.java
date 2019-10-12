/*
 * Filename AuthCacheFresh.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.common.annotation;

import java.lang.annotation.*;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthCacheFresh {
}
