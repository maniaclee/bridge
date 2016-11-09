package com.lvbby.bridge.api.param.parser;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.gateway.InjectProcessor;
import com.lvbby.bridge.util.BridgeUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lipeng on 16/11/9.
 */
public class InjectParamParser implements ParamsParser {
    private ParamsParser paramsParser;

    public InjectParamParser(ParamsParser paramsParser) {
        this.paramsParser = paramsParser;
    }

    @Override
    public String getType() {
        return paramsParser.getType();
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        return paramsParser.matchMethod(context, methodParameters);
    }

    @Override
    public Parameters parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        /**
         * parse params , filtered by inject processor
         * param parser doesn't know about value injection, so remove them first
         * */
        Parameters re = paramsParser.parse(context, filterValue(methodParameters));//filter value
        injectValue(re, methodParameters);
        return re;
    }

    @Override
    public MethodParameter[] getMethodParameter(ApiMethod apiMethod) {
        return paramsParser.getMethodParameter(apiMethod);
    }


    public MethodParameter[] filterValue(MethodParameter[] parameters) {
        List objects = InjectProcessor.getInjectValue();
        if (objects == null)
            return parameters;
        List<MethodParameter> re = Lists.newLinkedList();
        for (MethodParameter parameter : parameters) {
            if (!isInjectType(parameter.getType()))
                re.add(parameter);
        }
        return re.toArray(new MethodParameter[0]);
    }

    public void injectValue(Parameters parameters, MethodParameter[] methodParameters) {
        int paramSize = (parameters != null && parameters.getParameters() != null) ? parameters.getParameters().length : 0;
        /** no need to inject */
        if (paramSize == methodParameters.length)
            return;
        List injects = InjectProcessor.getInjectValue();
        if (injects == null)
            return;
        Parameter[] ps = new Parameter[methodParameters.length];
        //inject the value first
        for (MethodParameter methodParameter : methodParameters)
            for (Object value : injects)
                if (isInjectType(value, methodParameter.getType()))
                    ps[methodParameter.getIndex()] = new Parameter(value, methodParameter.getName());
        for (int i = 0, resultIndex = 0; i < paramSize; i++, resultIndex++) {
            while (resultIndex < ps.length && ps[resultIndex] != null)//skip the injected value
                ++resultIndex;
            if (resultIndex == ps.length)
                break;
            ps[resultIndex] = parameters.getParameters()[i];
        }
        parameters.setParameters(ps);

    }

    private boolean isInjectType(Object inject, Type type) {
        return BridgeUtil.isInstance(inject.getClass(), type) && !inject.getClass().equals(Object.class);
    }

    private boolean isInjectType(Type type) {
        List list = InjectProcessor.getInjectValue();
        if (list != null)
            for (Object o : list) {
                if (BridgeUtil.isInstance(o.getClass(), type) && !type.equals(Object.class))
                    return true;
            }
        return false;
    }
}
