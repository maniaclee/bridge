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
public class MapPreciseParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.MAP_PRECISE.getValue();
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getParam();
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
    public Parameters parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        List<Parameter> ps = Lists.newLinkedList();
        Map<String, String> map = (Map<String, String>) context.getRequest().getParam();

        for (MethodParameter methodParameter : methodParameters)
            ps.add(new Parameter(JSON.parseObject(map.get(methodParameter.getName()), methodParameter.getType()), methodParameter.getName()));
        return new Parameters(ps.toArray(new Parameter[0]));
    }
}
