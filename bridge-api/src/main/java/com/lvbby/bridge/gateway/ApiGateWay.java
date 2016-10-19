package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.exception.BridgeException;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    void init();

    Object proxy(Request request) throws BridgeException;

    ApiService getApiService(String serviceName);

    void addApiFilter(ApiGateWayFilter apiGateWayFilter);

    void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler);

    void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler);

}
