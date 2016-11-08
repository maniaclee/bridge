package com.lvbby.bridge.annotation;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.util.TypeCapable;

import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/30.
 */
public class AnnotationCapable<T extends Annotation> extends TypeCapable<T> {

    public T getAnnotation(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            T annotation = ((DefaultApiMethod) apiMethod).getMethod().getAnnotation(getType());
            return annotation;
        }
        return null;
    }
}
