package com.lvbby.bridge.filter;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/10/19.
 * * an annotation filter for blocking api service by Filter
 *
 * @see com.lvbby.bridge.annotation.Filter
 */
public class AnnotationApiGateWayFilter implements ApiGateWayFilter {
    private Class<? extends Annotation> annotation;

    public AnnotationApiGateWayFilter(Class annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean canVisit(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            Method method = ((DefaultApiMethod) apiMethod).getMethod();
            return method.getAnnotation(annotation) != null;
        }
        return false;
    }
}
