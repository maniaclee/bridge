package com.lvbby.bridge.http.filter;

import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridge;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AbstractHttpApiGateWayFilter implements ApiGateWayFilter {

    @Override
    public boolean canVisit(Context context) {
        HttpServletRequest request = (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_RESPONSE);
        if (request == null || response == null)
            throw new BridgeRunTimeException("HttpServletRequest or HttpServletResponse not found in ApiGateWay context, make sure you're using ApiGateWay in HttpBridge.");
        return canVisit(context, request, response);
    }

    public abstract boolean canVisit(Context context, HttpServletRequest request, HttpServletResponse response);
}
