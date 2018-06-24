package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSONObject;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.Validate;

import java.util.Map;

/**
 * Created by lipeng on 2018/6/16.
 */
public class JsonObjectParamsParser  implements ParamsParser{
    @Override
    public boolean matchMethod(ParamParsingContext context) {
        Object param = context.getRequest().getParam();
        Map<String, MethodParameter> parameterMap =context.getApiMethod().getParamAsMap();
        if(param==null){
            return parameterMap.isEmpty();
        }
        Validate.isTrue(param instanceof JSONObject,"param must be JSONObject");
        JSONObject jsonParam = (JSONObject) param;
        //过滤掉_开头的系统参数
        return jsonParam.keySet().stream().filter(s -> !s.startsWith("_")).map(Object::toString).allMatch(key -> parameterMap.containsKey(key));
    }

    public void addParameter(ParamParsingContext context, MethodParameter methodParameter, Object arg) {
        ensureParam(context).put(methodParameter.getName(), arg);
    }



    @Override
    public Object[] parse(ParamParsingContext context) {
        if (context.getApiMethod().getParamTypes().length == 0) {
            return null;
        }
        Request request = context.getRequest();
        JSONObject param = (JSONObject) request.getParam();
        Object[] re = new Object[context.getApiMethod().getParamTypes().length];
        for (int i = 0; i < context.getApiMethod().getParamTypes().length; i++) {
            MethodParameter parameterType = context.getApiMethod().getParamTypes()[i];
            //比较简单粗暴的方式
            Object value = param.getObject(parameterType.getName(),parameterType.getType());
            if (value != null) {
                re[i] = value;
            }
        }
        return re;
    }

    private JSONObject ensureParam(ParamParsingContext context) {
        if (context.getRequest().getParam() == null) {
            context.getRequest().setParam(new JSONObject());
        }
        return (JSONObject) context.getRequest().getParam();
    }

}
