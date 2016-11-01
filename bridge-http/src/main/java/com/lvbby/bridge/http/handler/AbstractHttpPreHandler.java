package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpBridgeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AbstractHttpPreHandler implements ApiGateWayPreHandler {
    @Override
    public void preProcess(Context context) {
        preProcess(context, HttpBridgeUtil.getHttpServletRequest(context), HttpBridgeUtil.getHttpServletResponse(context));
    }

    public abstract Object preProcess(Context context, HttpServletRequest request, HttpServletResponse response);
}
