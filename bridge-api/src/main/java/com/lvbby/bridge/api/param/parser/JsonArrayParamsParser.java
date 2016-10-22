package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.util.BridgeUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lipeng on 16/10/21.
 * jsonString
 */
public class JsonArrayParamsParser implements ParamsParser {
    private static final String type = ParamFormat.JSON_ARRAY.getValue();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getArg();
        if (arg instanceof String) {
            JSONArray jsonObject = JSON.parseArray(arg.toString());
            //TODO don't check parameter type for now
            return jsonObject.size() == methodParameters.length;
        }
        return false;
    }


    @Override
    public Params parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getArg();
        List<Object> jsonObject = JSON.parseArray(arg.toString(), BridgeUtil.getParameterTypes(methodParameters).toArray(new Type[0]));
        return Params.of(jsonObject.toArray());
    }

}
