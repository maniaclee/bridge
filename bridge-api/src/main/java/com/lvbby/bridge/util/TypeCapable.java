package com.lvbby.bridge.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by lipeng on 16/10/30.
 */
public class TypeCapable<T> {

    public Class<T> getType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
    }
}
