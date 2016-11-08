package com.lvbby.bridge.http.filter.anno;

import com.lvbby.bridge.annotation.AnnotationCapable;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridgeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class HttpAnnotationFilter<T extends Annotation> extends AnnotationCapable<T> implements ApiGateWayFilter {
    @Override
    public boolean canVisit(Context context) {
        T annotation = getAnnotation(context);
        if (annotation != null)
            return canVisit(context, annotation, HttpBridgeUtil.getHttpServletRequest(context), HttpBridgeUtil.getHttpServletResponse(context));
        return true;
    }

    public abstract boolean canVisit(Context context, T annotation, HttpServletRequest request, HttpServletResponse response);
}
