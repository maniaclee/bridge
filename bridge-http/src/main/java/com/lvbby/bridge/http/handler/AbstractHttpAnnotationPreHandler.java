package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.annotation.AbstractAnnotationCapable;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridgeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/30.
 */
public abstract class AbstractHttpAnnotationPreHandler<T extends Annotation> extends AbstractAnnotationCapable<T> implements ApiGateWayPreHandler {

    @Override
    public void preProcess(Context context) {
        T annotation = getAnnotation(context);
        if (annotation == null)
            return;
        preProcess(context, annotation, HttpBridgeUtil.getHttpServletRequest(context), HttpBridgeUtil.getHttpServletResponse(context));
    }

    public abstract Object preProcess(Context context, T t, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
