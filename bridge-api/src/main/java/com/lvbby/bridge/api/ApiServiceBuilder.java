package com.lvbby.bridge.api;

import com.lvbby.bridge.gateway.ApiGateWay;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiServiceBuilder {

    ApiGateWay addApiService(ApiService apiService);
}
