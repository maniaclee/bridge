package com.lvbby.bridge.http.annotation;

import com.lvbby.bridge.serializer.NoneSerializer;
import com.lvbby.bridge.serializer.Serializer;

import java.lang.annotation.*;

/**
 * Created by peng on 2016/10/4.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpLogin {
    /***
     * mark the session key
     */
    String sessionAttribute() default "";

    Class<? extends Serializer> serializer() default NoneSerializer.class;


}