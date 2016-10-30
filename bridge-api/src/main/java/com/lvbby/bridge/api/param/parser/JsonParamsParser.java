package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvbby.bridge.api.*;
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
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getArg();
        if (arg instanceof String) {
            JSONObject jsonObject = JSON.parseObject(arg.toString());
            if (jsonObject == null)
                return methodParameters.length == 0;
            /** match if only all the parameter name is contained in the paramter map */
            return BridgeUtil.getParameterNames(methodParameters).containsAll(jsonObject.keySet());
        }
        return false;
    }


    @Override
    public Parameters parse(ParamParsingContext context, MethodParameter[] parameters) {
        Object arg = context.getRequest().getArg();
        Parameter[] ps = new Parameter[parameters.length];
        JSONObject jsonObject = JSON.parseObject(arg.toString());
        for (MethodParameter methodParameter : parameters) {
            Object o = jsonObject.getObject(methodParameter.getName(), methodParameter.getType());
            ps[methodParameter.getIndex()] = new Parameter(o, methodParameter.getName());
        }
        return new Parameters(ps);
    }

}
