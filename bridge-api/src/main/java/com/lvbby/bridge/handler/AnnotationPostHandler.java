package com.lvbby.bridge.handler;

import com.lvbby.bridge.annotation.AnnotationCapable;
import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AnnotationPostHandler<T extends Annotation> extends AnnotationCapable<T> implements ApiGateWayPostHandler {

    @Override
    public Object success(Context context, Object result) {
        T annotation = getAnnotation(context);
        if (annotation != null)
            return success(context, annotation, result);
        return result;
    }

    public abstract Object success(Context context, T t, Object result);

}
