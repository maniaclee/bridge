package com.lvbby.bridge.http.tool;

import com.lvbby.bridge.exception.BridgeInterruptException;
import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridgeUtil;
import com.lvbby.bridge.http.annotation.HttpLogin;
import com.lvbby.bridge.http.handler.AbstractHttpAnnotationPostHandler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lipeng on 16/11/1.
 */
public class HttpLoginHandler extends AbstractHttpAnnotationPostHandler<HttpLogin> implements ApiGateWayPreHandler {
    @Override
    public void preProcess(Context context) {
        HttpLogin annotation = getAnnotation(context);
        if (annotation == null || HttpBridgeUtil.getHttpServletRequest(context) == null)
            return;
        String sessionAttribute = annotation.sessionAttribute();
        if (StringUtils.isBlank(sessionAttribute))
            throw new BridgeRunTimeException("session attribute is empty from " + annotation.getClass().getName());
        HttpSession session = HttpBridgeUtil.getHttpServletRequest(context).getSession();
        Object re = session.getAttribute(sessionAttribute);
        /** if already logged , return the result directly */
        if (re != null)
            throw new BridgeInterruptException("already logged in").setArg(re);
    }

    @Override
    public Object success(Context context, HttpLogin httpLogin, Object result, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            httpServletRequest.getSession(true).setAttribute(httpLogin.sessionAttribute(), httpLogin.serializer().newInstance().serialize(result));
        } catch (Exception e) {
            throw new BridgeRunTimeException(String.format("Can't save result to session because serializer can not be created. Serializer[%s] , Context[%s]", httpLogin.serializer().getName(), context));
        }
        return result;
    }
}
