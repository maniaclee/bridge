package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridgeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AbstractHttpPostHandler implements ApiGateWayPostHandler {

    @Override
    public Object success(Context context, Object result) {
        return success(context, result, HttpBridgeUtil.getHttpServletRequest(context), HttpBridgeUtil.getHttpServletResponse(context));
    }

    public abstract Object success(Context context, Object result, HttpServletRequest request, HttpServletResponse response);
}
