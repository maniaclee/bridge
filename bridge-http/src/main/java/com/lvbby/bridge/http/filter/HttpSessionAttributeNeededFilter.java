package com.lvbby.bridge.http.filter;

import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridge;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/19.
 * http request must have the give session key and the sessionAttribute must not be null
 */
public class HttpSessionAttributeNeededFilter implements ApiGateWayFilter {

    private String sessionKey;

    public HttpSessionAttributeNeededFilter(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Override
    public boolean canVisit(Context context) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
        return httpServletRequest != null && httpServletRequest.getSession() != null && httpServletRequest.getSession().getAttribute(sessionKey) != null;
    }
}
