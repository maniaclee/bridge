package com.lvbby.bridge.annotation;

import com.lvbby.bridge.util.ResultConverter;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Converter {

    /**
     * service name
     */
    Class<? extends ResultConverter> value();
}