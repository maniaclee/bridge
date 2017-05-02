package com.lvbby.bridge.http.annotation;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpUserIdParam {

    /**
     * if target is a custom Object, given the userId property name,
     * bridge will inject userId automatically
     */
    String propertyName() default "userId";

}