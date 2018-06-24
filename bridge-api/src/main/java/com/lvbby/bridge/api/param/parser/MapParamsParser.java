package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.Validate;

import java.util.Map;

/**
 * 不做类型转化，直接将map转为参数
 * Created by lipeng on 16/10/21.
 */
public class MapParamsParser implements ParamsParser {

    @Override
    public boolean matchMethod(ParamParsingContext context) {
        Map params = (Map) context.getRequest().getParam();
        Map<String, MethodParameter> parameterMap = context.getApiMethod().getParamAsMap();
        return params.keySet().stream().map(Object::toString).allMatch(key->parameterMap.containsKey(key) && parameterMap.get(key).match(params.get(key)));
    }

    public void addParameter(ParamParsingContext context, MethodParameter methodParameter, Object arg) {
        Map params = (Map) context.getRequest().getParam();
        params.put(methodParameter.getName(), arg);
    }

    @Override
    public Object[] parse(ParamParsingContext context) {
        if(context.getApiMethod().getParamTypes().length==0){
            return null;
        }
        Request request = context.getRequest();
        Object param = request.getParam();
        Validate.isTrue(param instanceof Map,"%s need Map parameters",getClass().getSimpleName());
        Map map = (Map) param;
        Object[] re = new Object[context.getApiMethod().getParamTypes().length];
        for (int i = 0; i < context.getApiMethod().getParamTypes().length; i++) {
            MethodParameter parameterType = context.getApiMethod().getParamTypes()[i];
            re[i]=map.get(parameterType.getName());
        }
        return re;
    }
}
