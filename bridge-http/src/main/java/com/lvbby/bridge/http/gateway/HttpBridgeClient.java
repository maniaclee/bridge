package com.lvbby.bridge.http.gateway;

import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.ApiServiceBuilder;
import com.lvbby.bridge.gateway.AbstractApiGateWay;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.Request;

import java.util.List;

/**
 * Created by lipeng on 16/11/9.
 */
public class HttpBridgeClient extends AbstractApiGateWay implements ApiGateWay  , ApiServiceBuilder{
    @Override
    public Object proxy(Request request) throws Exception {
        return null;
    }

    @Override
    public List<ApiService> getAllApiServices() {
        return null;
    }

    @Override
    public ApiService getApiService(String serviceName) {
        return null;
    }

    @Override
    public ApiGateWay addApiService(ApiService apiService) {
        return null;
    }
}
