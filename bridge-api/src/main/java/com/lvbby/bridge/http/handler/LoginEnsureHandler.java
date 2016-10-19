package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.filter.WhiteListApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridge;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/19.
 * some http url must be logged in !
 */
public class LoginEnsureHandler extends WhiteListApiGateWayFilter {

    private String userSessionKey;

    public LoginEnsureHandler(String userSessionKey) {
        this.userSessionKey = userSessionKey;
    }

    @Override
    public boolean canVisit(Context context) {
        if (!super.canVisit(context))
            return true;
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
        return httpServletRequest != null && httpServletRequest.getAttribute(userSessionKey) != null;
    }
}
