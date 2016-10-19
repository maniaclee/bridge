package com.lvbby.bridge.api.gateway;

import com.lvbby.bridge.api.gateway.Context;

/**
 * Created by peng on 2016/9/26.
 */
public interface ApiGateWayPreHandler {
    void preProcess(Context context);
}
