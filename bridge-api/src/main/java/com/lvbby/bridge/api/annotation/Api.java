package com.lvbby.bridge.api.annotation;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {

    String value() default "";

}