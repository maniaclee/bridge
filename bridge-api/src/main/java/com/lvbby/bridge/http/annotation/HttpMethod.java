package com.lvbby.bridge.http.annotation;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpMethod {
    String GET = "get";
    String POST = "post";
    String DELETE = "delete";
    String PUT = "put";


    String value() default GET;

}