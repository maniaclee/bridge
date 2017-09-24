package com.lvbby.bridge.spring;

/**
 * Created by lipeng on 2017/9/24.
 */

import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interceptor {
    /***
     * class
     * @return
     */
    Class<? extends MethodInterceptor>[] clz() default {};

    String[] bean() default {};
}
