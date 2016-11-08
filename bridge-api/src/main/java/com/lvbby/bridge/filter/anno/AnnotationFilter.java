package com.lvbby.bridge.filter.anno;

import com.lvbby.bridge.annotation.AnnotationCapable;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AnnotationFilter<T extends Annotation> extends AnnotationCapable<T> implements ApiGateWayFilter {
    @Override
    public boolean canVisit(Context context) {
        T annotation = getAnnotation(context);
        if (annotation != null)
            return canVisit(context, annotation);
        return true;
    }

    public abstract boolean canVisit(Context context, T t);

}
