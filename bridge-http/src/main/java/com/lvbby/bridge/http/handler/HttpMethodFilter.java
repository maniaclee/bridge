package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridge;
import com.lvbby.bridge.http.annotation.HttpMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal value of request.requestKey && session.sessionKey
 */
public class HttpMethodFilter implements ApiGateWayFilter {

    @Override
    public boolean canVisit(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            HttpMethod annotation = ((DefaultApiMethod) apiMethod).getMethod().getAnnotation(HttpMethod.class);
            if (annotation == null)
                return true;
            HttpServletRequest httpServletRequest = (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
            return httpServletRequest.getMethod().equalsIgnoreCase(annotation.value());
        }
        return true;
    }

}
