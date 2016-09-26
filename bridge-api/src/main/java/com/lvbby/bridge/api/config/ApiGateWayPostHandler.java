package com.lvbby.bridge.api.config;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.wrapper.Context;

/**
 * Created by peng on 16/9/23.
 */
public interface ApiGateWayPostHandler {
    Object success(Context context, Object result);

    Object error(Context context, Object result, BridgeException e) throws BridgeException;
}
