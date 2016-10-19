package com.lvbby.bridge.api.gateway;

import com.lvbby.bridge.api.config.ApiGateWayPreHandler;
import com.lvbby.bridge.api.config.ApiGateWayPostHandler;
import com.lvbby.bridge.api.exception.BridgeException;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    void init();

    Object proxy(Context context) throws BridgeException;

    ServiceRouter getServiceRouter();

    ApiService getApiService(String serviceName);

    void addApiFilter(ApiFilter apiFilter);

    void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler);

    void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler);

}
