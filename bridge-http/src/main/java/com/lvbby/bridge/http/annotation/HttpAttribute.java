package com.lvbby.bridge.http.annotation;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpAttribute {

    /***
     * mark the request key
     *
     * @return
     */
    String requestAttribute() default "";

    /***
     * mark the session key
     *
     * @return
     */
    String sessionAttribute() default "";

}