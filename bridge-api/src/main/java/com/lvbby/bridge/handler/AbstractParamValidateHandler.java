package com.lvbby.bridge.handler;

import com.lvbby.bridge.api.Parameter;
import com.lvbby.bridge.api.Parameters;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 */
public abstract class AbstractParamValidateHandler implements ApiGateWayPreHandler {

    @Override
    public void preProcess(Context request) {
        Parameters param = request.getParameters();
        if (param == null || param.getParameters() == null || param.getParameters().length == 0)
            return;
        for (Parameter p : param.getParameters()) {
            processParam(request, p);
        }
    }

    public abstract boolean processParam(Context request, Parameter p);
}
