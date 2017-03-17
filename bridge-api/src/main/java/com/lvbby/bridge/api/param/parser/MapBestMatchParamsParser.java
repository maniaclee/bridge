package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvbby.bridge.api.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 */
public class MapBestMatchParamsParser extends AbstractParamsParser {
    private static final String type = ParamFormat.MAP_BEST_MATCH.getValue();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getParam();
        return arg instanceof Map;
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
