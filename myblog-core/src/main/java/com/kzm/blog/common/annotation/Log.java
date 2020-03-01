package com.kzm.blog.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: kouzm
 * @Description: 日志注解
 * @Date: Created in 12:54 2020/2/25
 * @Version
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String module() default "" ;

    String operation() default "";

}
