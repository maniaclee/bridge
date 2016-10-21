package com.lvbby.bridge.api.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.BridgeUtil;

/**
 * Created by lipeng on 16/10/21.
 * jsonString
 */
public class JsonParamsParser implements ParamsParser {
    private static final String type = ParamFormat.JSON.getValue();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean matchMethod(Request request, ApiMethod apiMethod) {
        Object arg = request.getArg();
        if (arg instanceof String) {
            JSONObject jsonObject = JSON.parseObject(arg.toString());
            return BridgeUtil.equalCollection(jsonObject.keySet(), BridgeUtil.getParameterNames(apiMethod));
        }
        return false;
    }


    @Override
    public Params parse(Request request, ApiMethod apiMethod) {
        Object arg = request.getArg();
        Param[] ps = new Param[apiMethod.getParamTypes().length];
        JSONObject jsonObject = JSON.parseObject(arg.toString());
        for (MethodParameter methodParameter : apiMethod.getParamTypes()) {
            Object o = jsonObject.getObject(methodParameter.getName(), methodParameter.getType());
            ps[methodParameter.getIndex()] = new Param(o, methodParameter.getName());
        }
        return new Params(ps);
    }

}
