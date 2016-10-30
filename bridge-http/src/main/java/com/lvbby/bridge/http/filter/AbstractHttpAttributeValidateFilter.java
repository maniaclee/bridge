package com.lvbby.bridge.http.filter;

import com.lvbby.bridge.gateway.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal sessionAttribute of request.requestKey && session.sessionKey
 */
public abstract class AbstractHttpAttributeValidateFilter extends AbstractHttpApiGateWayFilter {


    @Override
    public boolean canVisit(Context context, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String sessionKey = getSessionKey(context);
        return httpServletRequest.getSession() != null
                && httpServletRequest.getSession().getAttribute(sessionKey) != null
                && httpServletRequest.getSession().getAttribute(sessionKey).equals(httpServletRequest.getAttribute(getRequestKey(context)));
    }

    public abstract String getRequestKey(Context context);

    public abstract String getSessionKey(Context context);

}
