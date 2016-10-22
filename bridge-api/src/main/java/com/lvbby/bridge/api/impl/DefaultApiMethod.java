package com.lvbby.bridge.api.impl;

import com.google.common.base.Objects;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.api.param.extracotr.DefaultParameterNameExtractor;
import com.lvbby.bridge.exception.BridgeException;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lipeng on 16/9/23.
 * wrapper for value
 */
public class DefaultApiMethod implements ApiMethod {
    /**
     * default parameterName finder
     */
    private static final ParameterNameExtractor defaultParameterNameExtractor = new DefaultParameterNameExtractor();
    private Method method;
    private String name;
    private MethodParameter[] methodParameters;
    private Map<String, MethodParameter> parameterMap = new LinkedHashMap<String, MethodParameter>();
    private ParameterNameExtractor parameterNameExtractor = defaultParameterNameExtractor;

    public DefaultApiMethod(Method method) {
        this.method = method;
        this.name = method.getName();
        method.setAccessible(true);
    }

    public DefaultApiMethod init() {
        Class<?>[] parameterTypes = method.getParameterTypes();
        methodParameters = new MethodParameter[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            MethodParameter methodParameter = new MethodParameter();
            methodParameter.setIndex(i);
            methodParameter.setType(parameterTypes[i]);
            methodParameters[i] = methodParameter;
            //map for parameter name
            parameterMap.put(methodParameter.getName(), methodParameter);
        }
        /** parameter names */
        if (parameterNameExtractor != null) {
            String[] parameterNames = parameterNameExtractor.getParameterName(method);
            if (parameterNames == null || parameterNames.length != methodParameters.length)
                throw new IllegalArgumentException("invalid value parameter length : " + method);
            for (int i = 0; i < methodParameters.length; i++)
                methodParameters[i].setName(parameterNames[i]);
        }
        return this;
    }


    /***
     * get real parameters for value to invoke
     *
     * @param params
     * @return
     */
    public Object[] getRealParameters(Params params) {
        Param[] ps = params.getParams();
        //void
        if ((ps == null || ps.length == 0))
            return null;

        Object[] re = new Object[ps.length];
        //match by index
        if (Objects.equal(params.getType(), Params.byIndex)) {
            for (int i = 0; i < ps.length; i++) re[i] = ps[i].getParam();
            return re;
        }
        //match by name
        if (Objects.equal(params.getType(), Params.byName)) {
            for (Param p : ps) {
                MethodParameter methodParameter = parameterMap.get(p.getName());
                re[methodParameter.getIndex()] = p.getParam();

            }
            return re;
        }
        throw new IllegalArgumentException("unknown parameter type : " + params.getType());
    }

    @Override
    public Object invoke(ApiService apiService, Params params) throws Exception {
        Object[] realParameters = getRealParameters(params);
        try {
            return method.invoke(apiService.getService(), realParameters);
        } catch (Exception e) {
            throw new BridgeException(String.format("error invoke %s.%s", apiService.getServiceName(), getName()),e);
        }
    }

    @Override
    public MethodParameter[] getParamTypes() {
        MethodParameter[] re = new MethodParameter[this.methodParameters.length];
        System.arraycopy(this.methodParameters, 0, re, 0, methodParameters.length);
        return re;
    }


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MethodParameter[] getMethodParameters() {
        return methodParameters;
    }

    public void setMethodParameters(MethodParameter[] methodParameters) {
        this.methodParameters = methodParameters;
    }

    public ParameterNameExtractor getParameterNameExtractor() {
        return parameterNameExtractor;
    }

    public void setParameterNameExtractor(ParameterNameExtractor parameterNameExtractor) {
        this.parameterNameExtractor = parameterNameExtractor;
    }


}
