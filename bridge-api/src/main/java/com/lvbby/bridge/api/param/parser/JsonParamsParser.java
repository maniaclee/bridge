package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 * 输入param= {param1:{},param2:'string',param3:3}
 */
public class JsonParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.JSON.getValue();
    }

    @Override
    public boolean match(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        if (arg instanceof String) {
            JSONObject jsonObject = JSON.parseObject(arg.toString());
            if (jsonObject == null)
                return context.findRealParameterTypes().isEmpty();
            /** match if only all the parameter name is contained in the parameter map */
            return context.findRealParameterNames().containsAll(jsonObject.keySet());
        }
        return false;
    }


    @Override
    public Parameters doParse(ParamParsingContext context) {
        JSONObject jsonObject = JSON.parseObject(context.getRequest().getParam().toString());
        List<MethodParameter> realParameters = context.findRealParameters();
        Map<String, Object> re = Maps.newHashMap();
        for (MethodParameter methodParameter : realParameters) {
            Object o = jsonObject.getObject(methodParameter.getName(), methodParameter.getType());
            re.put(methodParameter.getName(), o);
        }
        return Parameters.ofMap(context.getApiMethod(), re);
    }

}
