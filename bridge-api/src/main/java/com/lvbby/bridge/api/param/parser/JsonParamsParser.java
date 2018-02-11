package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSONObject;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.Validate;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 嵌套map的参数类型
 * Created by lipeng on 16/10/21.
 */
public class JsonParamsParser implements ParamsParser {

    @Override
    public boolean matchMethod(ParamParsingContext context) {
        Map params = (Map) context.getRequest().getParam();
        Map<String, MethodParameter> parameterMap = Arrays.stream(context.getApiMethod().getParamTypes())
            .collect(Collectors.toMap(o -> o.getName(), Function.identity()));
        if (params == null) {
            return parameterMap.isEmpty();
        }
        return params.keySet().stream().map(Object::toString).allMatch(key -> parameterMap.containsKey(key));
    }

    public void addParameter(ParamParsingContext context, MethodParameter methodParameter, Object arg) {
        Map params = (Map) context.getRequest().getParam();
        params.put(methodParameter.getName(), arg);
    }

    @Override
    public Object[] parse(ParamParsingContext context) {
        if (context.getApiMethod().getParamTypes().length == 0) {
            return null;
        }
        Request request = context.getRequest();
        Object param = request.getParam();
        Validate.isTrue(param instanceof Map, "%s need Map parameters", getClass().getSimpleName());
        Map map = (Map) param;
        Object[] re = new Object[context.getApiMethod().getParamTypes().length];
        for (int i = 0; i < context.getApiMethod().getParamTypes().length; i++) {
            MethodParameter parameterType = context.getApiMethod().getParamTypes()[i];
            //比较简单粗暴的方式
            Object value = map.get(parameterType.getName());
            if (value != null) {
                re[i] = parse(parameterType.getType(),value);
            }
        }
        return re;
    }

    private static Object parse(Class clz , Object object){
        if(object==null)
            return null;
        if(clz.isAssignableFrom(object.getClass()))
            return object;
        if(object instanceof Map){
            return new JSONObject((Map<String, Object>) object).toJavaObject(clz);
        }
        throw new IllegalArgumentException("can't parse object");
    }

}
