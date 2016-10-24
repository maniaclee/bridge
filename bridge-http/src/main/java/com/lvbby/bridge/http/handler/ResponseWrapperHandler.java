package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.ServiceResponse;

/**
 * Created by peng on 2016/9/27.
 */
public class ResponseWrapperHandler implements ApiGateWayPostHandler {
    @Override
    public Object success(Context context, Object result) {
        return ServiceResponse.success(result);
    }

}
