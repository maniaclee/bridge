package com.lvbby.bridge.gateway;

import com.lvbby.bridge.exception.BridgeException;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    void init();

    Object proxy(Context context) throws BridgeException;

    ApiService getApiService(String serviceName);

    void addApiFilter(ApiFilter apiFilter);

    void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler);

    void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler);

}
