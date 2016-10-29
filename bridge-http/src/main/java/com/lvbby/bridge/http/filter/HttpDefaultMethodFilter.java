package com.lvbby.bridge.http.filter;

import com.lvbby.bridge.gateway.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/30.
 * default method for api gateway.
 */
public class HttpDefaultMethodFilter extends AbstractHttpApiGateWayFilter {
    private String method;

    public HttpDefaultMethodFilter(String method) {
        this.method = method;
    }

    @Override
    public boolean canVisit(Context context, HttpServletRequest request, HttpServletResponse response) {
        return request.getMethod().equalsIgnoreCase(method);
    }
}
