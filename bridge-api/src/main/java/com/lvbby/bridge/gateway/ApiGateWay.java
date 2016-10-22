package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ApiService;

import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    void init();

    Object proxy(Request request) throws Exception;


    List<ApiService> getAllApiServices();

    ApiService getApiService(String serviceName);

    void addApiFilter(ApiGateWayFilter apiGateWayFilter);

    void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler);

    void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler);

}
