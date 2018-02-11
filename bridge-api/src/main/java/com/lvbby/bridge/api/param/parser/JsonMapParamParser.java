package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.util.Validate;
import org.apache.commons.lang3.ClassUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {a:jsonString,b:jsonString}
 * Created by lipeng on 2018/2/11.
 */
public class JsonMapParamParser extends JsonParamsParser {

    @Override
    public Object[] parse(ParamParsingContext context) {
        if (context.getApiMethod().getParamTypes().length == 0) {
            return null;
        }
        Map<String, Object> param = (Map<String, Object>) context.getRequest().getParam();
        Object[] re = new Object[context.getApiMethod().getParamTypes().length];

        Map<String, MethodParameter> parameterMap = Arrays.stream(context.getApiMethod().getParamTypes()).collect(Collectors.toMap(o -> o.getName(), Function.identity()));
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            MethodParameter methodParameter = parameterMap.get(entry.getKey());
            Validate.notNull(methodParameter, "invalid param : %s", entry.getKey());
            if (entry.getValue() instanceof String) {
                re[methodParameter.getIndex()] = JSON.parseObject(entry.getValue().toString(), methodParameter.getType());
                continue;
            }
            if (ClassUtils.isAssignable(entry.getValue().getClass(), methodParameter.getType())) {
                re[methodParameter.getIndex()] = entry.getValue();
                continue;
            }
        }
        return re;
    }
}
