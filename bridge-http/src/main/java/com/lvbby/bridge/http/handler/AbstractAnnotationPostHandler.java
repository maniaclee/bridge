package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.annotation.AbstractAnnotationCapable;
import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/30.
 */
public abstract class AbstractAnnotationPostHandler<T extends Annotation> extends AbstractAnnotationCapable<T> implements ApiGateWayPostHandler {
    @Override
    public Object success(Context context, Object result) {
        T annotation = getAnnotation(context);
        if (annotation != null)
            return success(context, annotation, result);
        return result;
    }

    public abstract boolean success(Context context, T t, Object result);

}
