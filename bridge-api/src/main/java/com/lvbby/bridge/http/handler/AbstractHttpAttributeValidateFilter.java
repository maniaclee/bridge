package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridge;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal value of request.requestKey && session.sessionKey
 */
public abstract class AbstractHttpAttributeValidateFilter implements ApiGateWayFilter {


    @Override
    public boolean canVisit(Context context) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
        String sessionKey = getSessionKey(context);
        return httpServletRequest != null && httpServletRequest.getSession() != null
                && httpServletRequest.getSession().getAttribute(sessionKey) != null
                && httpServletRequest.getSession().getAttribute(sessionKey).equals(httpServletRequest.getAttribute(getRequestKey(context)));
    }

    public abstract String getRequestKey(Context context);

    public abstract String getSessionKey(Context context);

}
