package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.Parameters;
import com.lvbby.bridge.util.BridgeUtil;

import java.util.Map;

/**
 * Created by lipeng on 2017/3/21.
 */
public class MapWrapperParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.MAP_WRAPPER.getValue();
    }

    @Override
    public boolean match(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        return arg instanceof Map;
    }

    @Override
    public Parameters doParse(ParamParsingContext context) {
        Map map = (Map) context.getRequest().getParam();
        //类型转化
        for (MethodParameter methodParameter : context.findRealParameters()) {
            String key = methodParameter.getName();
            Object value = map.get(key);
            map.put(key, BridgeUtil.convertValueForType(methodParameter.getType(), value));
        }
        return Parameters.ofMap(context.getApiMethod(), map);
    }

}