package com.lvbby.bridge.annotation;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamCheck {


    boolean pass() default true;

}