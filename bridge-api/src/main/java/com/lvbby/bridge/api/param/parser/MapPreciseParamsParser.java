package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.Parameters;

import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 * 输入param= Map ， 精确匹配，参数一定要全
 */
public class MapPreciseParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.MAP_PRECISE.getValue();
    }

    @Override
    public boolean match(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        if (arg instanceof Map) {
            Map<String, String> map = (Map<String, String>) arg;
            for (MethodParameter methodParameter : context.findRealParameters()) {
                if (!map.containsKey(methodParameter.getName()))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Parameters doParse(ParamParsingContext context) {
        Map map = (Map) context.getRequest().getParam();

        Map re = Maps.newHashMap();
        for (MethodParameter methodParameter : context.findRealParameters())
            re.put(methodParameter.getName(), JSON.parseObject(map.get(methodParameter.getName()).toString(), methodParameter.getType()));
        return Parameters.ofMap(context.getApiMethod(), re);
    }
}
