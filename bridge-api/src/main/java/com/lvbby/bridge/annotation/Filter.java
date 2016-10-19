package com.lvbby.bridge.annotation;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {

    /**
     * black list method
     */
    String BlackList = "BlackList";
    /***
     * white list method
     */
    String WhiteList = "WhiteList";

    String value() default BlackList;

}