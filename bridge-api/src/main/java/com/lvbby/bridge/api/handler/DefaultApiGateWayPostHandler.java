package com.lvbby.bridge.api.handler;

import com.lvbby.bridge.api.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.api.gateway.Context;

/**
 * convert result
 * Created by peng on 16/9/23.
 */
public class DefaultApiGateWayPostHandler implements ApiGateWayPostHandler {

    @Override
    public Object success(Context context, Object result) {
        return result;
    }

    @Override
    public Object error(Context context, Object result, Exception e) throws Exception {
        return result;
    }
}
