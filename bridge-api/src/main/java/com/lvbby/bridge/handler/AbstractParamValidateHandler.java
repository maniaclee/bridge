package com.lvbby.bridge.handler;

import com.lvbby.bridge.api.Param;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 */
public abstract class AbstractParamValidateHandler implements ApiGateWayPreHandler {

    @Override
    public void preProcess(Context request) {
        Params param = request.getParams();
        if (param == null || param.getParams() == null || param.getParams().length == 0)
            return;
        for (Param p : param.getParams()) {
            processParam(request, p);
        }
    }

    public abstract boolean processParam(Context request, Param p);
}
