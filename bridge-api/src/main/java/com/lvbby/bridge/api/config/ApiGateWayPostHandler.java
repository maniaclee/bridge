package com.lvbby.bridge.api.config;

import com.lvbby.bridge.api.gateway.Context;

/**
 * Created by peng on 16/9/23.
 */
public interface ApiGateWayPostHandler {
    Object success(Context context, Object result);

    Object error(Context context, Object result, Exception e) throws Exception;
}
