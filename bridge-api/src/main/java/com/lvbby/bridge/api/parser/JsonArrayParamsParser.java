package com.lvbby.bridge.api.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.gateway.Request;
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
    public boolean matchMethod(Request request, ApiMethod apiMethod) {
        Object arg = request.getArg();
        if (arg instanceof String) {
            JSONArray jsonObject = JSON.parseArray(arg.toString());
            //TODO don't check parameter type for now
            return jsonObject.size() == apiMethod.getParamTypes().length;
        }
        return false;
    }


    @Override
    public Params parse(Request request, ApiMethod apiMethod) {
        Object arg = request.getArg();
        List<Object> jsonObject = JSON.parseArray(arg.toString(), BridgeUtil.getParameterTypes(apiMethod).toArray(new Type[0]));
        return Params.of(jsonObject.toArray());
    }

}
