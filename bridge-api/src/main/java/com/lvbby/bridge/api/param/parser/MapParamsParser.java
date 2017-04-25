package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.*;

import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 */
public class MapParamsParser implements ParamsParser {

    @Override
    public String getType() {
        return ParamFormat.Map.getValue();
    }

    @Override
    public boolean matchMethod(ParamParsingContext context) {
        MethodParameter[] paramTypes = context.getApiMethod().getParamTypes();
        Map params = (Map) context.getRequest().getParam();
        if (paramTypes.length == 0)
            return params == null || params.isEmpty();
        if (params == null || params.isEmpty())
            return false;
        for (MethodParameter methodParameter : paramTypes) {
            if (!params.containsKey(methodParameter.getName()))
                return false;
        }
        return true;
    }

    public void addParameter(ParamParsingContext context, MethodParameter methodParameter, Object arg) {
        Map params = (Map) context.getRequest().getParam();
        params.put(methodParameter.getName(), arg);
    }

    @Override
    public Parameters parse(ParamParsingContext context) {
        return Parameters.ofMap(context.getApiMethod(), (Map) context.getRequest().getParam());
    }
}
