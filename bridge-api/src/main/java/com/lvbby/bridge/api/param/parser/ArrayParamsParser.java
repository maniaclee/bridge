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
    public boolean matchMethod(ParamParsingContext context) {
        MethodParameter[] paramTypes = context.getApiMethod().getParamTypes();

        Object[] params = (Object[]) context.getRequest().getParam();
        if (params == null) {
            return paramTypes.length == 0;
        }
        if (params.length != paramTypes.length)
            return false;
        for (int i = 0; i < params.length; i++) {
            if (!paramTypes[i].match(params[i])) {
                return false;
            }
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
    public Object[] parse(ParamParsingContext context) {
        return (Object[]) context.getRequest().getParam();
    }
}
