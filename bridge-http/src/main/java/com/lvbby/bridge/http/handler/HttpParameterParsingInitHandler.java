package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.ApiGateWayInitHandler;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.BridgeUtil;

import java.util.Map;

/**
 * Created by lipeng on 2017/4/27.
 */
public class HttpParameterParsingInitHandler implements ApiGateWayInitHandler {
    @Override
    public void handle(ParamParsingContext paramParsingContext) throws BridgeRoutingException {
        MethodParameter[] paramTypes = paramParsingContext.getApiMethod().getParamTypes();
        Request request = paramParsingContext.getRequest();
        if (ParamFormat.Map.getValue().equals(request.getParamType())) {
            Map<String, Object> params = (Map<String, Object>) request.getParam();
            for (MethodParameter paramType : paramTypes) {
                if (params.containsKey(paramType.getName())) {
                    Object value = params.get(paramType.getName());
                    //convert
                    params.put(paramType.getName(), BridgeUtil.convertValueForType(paramType.getType(), value));
                }
            }
        } else if (ParamFormat.Array.getValue().equals(request.getParamType())) {
        }
    }
}
