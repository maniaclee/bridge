package com.lvbby.bridge.api.param.parser;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.*;

import java.util.ArrayList;

/**
 * Created by lipeng on 16/10/21.
 * simply use ParamParsingContext.getRequest().getParam() as the parameters
 */
public class ArrayParamsParser implements ParamsParser {

    @Override
    public String getType() {
        return ParamFormat.Array.getValue();
    }

    @Override
    public boolean matchMethod(ParamParsingContext context) {
        MethodParameter[] paramTypes = context.getApiMethod().getParamTypes();

        Object[] params = (Object[]) context.getRequest().getParam();
        if (paramTypes.length == 0)
            return params == null || params.length == 0;
        if (params == null || params.length == 0)
            return false;
        if (params.length != paramTypes.length)
            return false;
        for (int i = 0; i < paramTypes.length; i++) {
            Object param = params[i];
            if (param != null && !paramTypes[i].getType().isAssignableFrom(param.getClass()))
                return false;
        }
        return true;
    }

    @Override
    public void addParameter(ParamParsingContext context, MethodParameter methodParameter, Object arg) {
        Object[] params = (Object[]) context.getRequest().getParam();
        ArrayList<Object> objects = Lists.newArrayList(params);
        objects.add(methodParameter.getIndex(), arg);
        context.getRequest().setParam(objects.toArray());
    }

    @Override
    public Parameters parse(ParamParsingContext context) {
        return Parameters.of(context.getApiMethod(), (Object[]) context.getRequest().getParam());
    }
}
