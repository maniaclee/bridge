package com.lvbby.bridge.handler;

import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 * the wrapper of ApiGateWayFilter && ApiGateWayPreHandler
 * compose the filterable ApiGateWayPreHandler
 */
public class FilterHandler implements ApiGateWayFilter, ApiGateWayPreHandler, ApiGateWayPostHandler {

    private ApiGateWayFilter apiGateWayFilter;
    private ApiGateWayPreHandler apiGateWayPreHandler;
    private ApiGateWayPostHandler apiGateWayPostHandler;


    @Override
    public boolean canVisit(Context context) {
        return apiGateWayFilter == null || apiGateWayFilter.canVisit(context);
    }

    @Override
    public void preProcess(Context request) {
        if (canVisit(request) && apiGateWayPreHandler != null)
            apiGateWayPreHandler.preProcess(request);
    }

    @Override
    public Object success(Context context, Object result) {
        if (canVisit(context) && apiGateWayPostHandler != null)
            return apiGateWayPostHandler.success(context, result);
        return result;
    }

    public ApiGateWayFilter getApiGateWayFilter() {
        return apiGateWayFilter;
    }

    public void setApiGateWayFilter(ApiGateWayFilter apiGateWayFilter) {
        this.apiGateWayFilter = apiGateWayFilter;
    }

    public ApiGateWayPreHandler getApiGateWayPreHandler() {
        return apiGateWayPreHandler;
    }

    public void setApiGateWayPreHandler(ApiGateWayPreHandler apiGateWayPreHandler) {
        this.apiGateWayPreHandler = apiGateWayPreHandler;
    }

    public ApiGateWayPostHandler getApiGateWayPostHandler() {
        return apiGateWayPostHandler;
    }

    public void setApiGateWayPostHandler(ApiGateWayPostHandler apiGateWayPostHandler) {
        this.apiGateWayPostHandler = apiGateWayPostHandler;
    }
}
