package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridge;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal value of request.requestKey && session.sessionKey
 */
public class RequestAttributeValidateFilter implements ApiGateWayFilter {

    private String sessionKey;
    private String requestKey;

    public RequestAttributeValidateFilter(String sessionKey, String requestKey) {
        this.sessionKey = sessionKey;
        this.requestKey = requestKey;
    }

    @Override
    public boolean canVisit(Context context) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
        return httpServletRequest != null && httpServletRequest.getSession() != null
                && httpServletRequest.getSession().getAttribute(sessionKey) != null
                && httpServletRequest.getSession().getAttribute(sessionKey).equals(httpServletRequest.getAttribute(requestKey));
    }
}
