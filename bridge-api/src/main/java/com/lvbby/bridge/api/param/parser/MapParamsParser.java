package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvbby.bridge.api.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 * Map<String,String>  key -> jsonString
 */
public class MapParamsParser implements ParamsParser {
    private static final String type = ParamFormat.MAP.getValue();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getArg();
        if (arg instanceof Map) {
            Map<String, String> map = (Map<String, String>) arg;
            for (MethodParameter methodParameter : methodParameters) {
                if (!map.containsKey(methodParameter.getName()))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Params parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        List<Param> ps = Lists.newLinkedList();
        Map<String, String> map = (Map<String, String>) context.getRequest().getArg();

        for (MethodParameter methodParameter : methodParameters)
            ps.add(new Param(JSON.parseObject(map.get(methodParameter.getName()), methodParameter.getType()), methodParameter.getName()));
        return new Params(ps.toArray(new Param[0]));
    }
}
