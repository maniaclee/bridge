package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.handler.AbstractAnnotationPostHanlder;
import com.lvbby.bridge.http.HttpBridgeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Created by lipeng on 16/10/30.
 */
public abstract class AbstractHttpAnnotationPostHandler<T extends Annotation> extends AbstractAnnotationPostHanlder<T> {

    public Object success(Context context, T t, Object result) {
        return success(context, t, result, HttpBridgeUtil.getHttpServletRequest(context), HttpBridgeUtil.getHttpServletResponse(context));
    }

    public abstract Object success(Context context, T t, Object result, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);


}
