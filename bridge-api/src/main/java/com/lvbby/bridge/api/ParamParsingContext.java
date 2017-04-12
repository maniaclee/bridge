package com.lvbby.bridge.api;

import com.google.common.collect.Lists;
import com.lvbby.bridge.gateway.InjectProcessor;
import com.lvbby.bridge.gateway.Request;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/10/21.
 */
public class ParamParsingContext {
    private Request request;
    private ApiMethod apiMethod;

    public ParamParsingContext(Request request) {
        this.request = request;
    }

    public List<MethodParameter> findRealParameters() {
        return _findRealParameter(p -> p);
    }

    public List<Class> findRealParameterTypes() {
        return _findRealParameter(p -> p.getType());
    }

    public List<String> findRealParameterNames() {
        return _findRealParameter(p -> p.getName());
    }

    private <T> List<T> _findRealParameter(Function<MethodParameter, T> function) {
        return Lists.newArrayList(apiMethod.getParamTypes()).stream().filter(p -> !InjectProcessor.isInjectType(p.getType())).map(function).collect(Collectors.toList());
    }


    public ParamParsingContext method(ApiMethod apiMethod) {
        setApiMethod(apiMethod);
        return this;
    }

    public ApiMethod getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(ApiMethod apiMethod) {
        this.apiMethod = apiMethod;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
