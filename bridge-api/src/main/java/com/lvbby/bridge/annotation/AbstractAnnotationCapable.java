package com.lvbby.bridge.annotation;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;

/**
 * Created by lipeng on 16/10/30.
 */
public class AbstractAnnotationCapable<T extends Annotation> {

    public T getAnnotation(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            T annotation = ((DefaultApiMethod) apiMethod).getMethod().getAnnotation(getType());
            return annotation;
        }
        return null;
    }

    public Class<T> getType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
    }
}
