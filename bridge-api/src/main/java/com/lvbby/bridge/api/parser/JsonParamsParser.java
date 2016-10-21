package com.lvbby.bridge.api.parser;

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
            return BridgeUtil.equalCollection(jsonObject.keySet(), BridgeUtil.getParameterNames(methodParameters));
        }
        return false;
    }


    @Override
    public Params parse(ParamParsingContext context, MethodParameter[] parameters) {
        Object arg = context.getRequest().getArg();
        Param[] ps = new Param[parameters.length];
        JSONObject jsonObject = JSON.parseObject(arg.toString());
        for (MethodParameter methodParameter : parameters) {
            Object o = jsonObject.getObject(methodParameter.getName(), methodParameter.getType());
            ps[methodParameter.getIndex()] = new Param(o, methodParameter.getName());
        }
        return new Params(ps);
    }

}
