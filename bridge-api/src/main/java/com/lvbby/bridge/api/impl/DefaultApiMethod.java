package com.lvbby.bridge.api.impl;

import com.lvbby.bridge.annotation.BridgeMethod;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.api.param.extracotr.AnnotationParameterNameExtractor;
import com.lvbby.bridge.api.param.extracotr.DefaultParameterNameExtractor;
import com.lvbby.bridge.exception.BridgeInvokeException;

import java.lang.reflect.InvocationTargetException;
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
    private static final ParameterNameExtractor annotationParameterNameExtractor = new AnnotationParameterNameExtractor();
    private Method method;
    private String name;
    private MethodParameter[] methodParameters;
    private Map<String, MethodParameter> parameterMap = new LinkedHashMap<String, MethodParameter>();
    private ParameterNameExtractor parameterNameExtractor = defaultParameterNameExtractor;

    public DefaultApiMethod(Method method) {
        this.method = method;
        this.name = getMethodName();
        method.setAccessible(true);
    }

    private String getMethodName() {
        BridgeMethod annotation = method.getAnnotation(BridgeMethod.class);
        if (annotation != null)
            return annotation.value();
        return method.getName();
    }

    public DefaultApiMethod init() {
        Class<?>[] parameterTypes = method.getParameterTypes();
        methodParameters = new MethodParameter[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            MethodParameter methodParameter = new MethodParameter();
            methodParameter.setIndex(i);
            methodParameter.setType(parameterTypes[i]);
            methodParameter.setMethod(method);
            methodParameters[i] = methodParameter;
        }
        /** parameter names, only available when method has parameters*/
        if (parameterNameExtractor != null && methodParameters.length > 0) {
            String[] parameterNames = parameterNameExtractor.getParameterName(method);
            if (parameterNames == null || parameterNames.length == 0 || parameterNames.length != methodParameters.length)
                throw new IllegalArgumentException("invalid value parameter length : " + method);

            /** annotation parameters will overwrite the others, annotation as the native way */
            String[] annotationParamNames = annotationParameterNameExtractor.getParameterName(method);
            for (int i = 0; i < parameterNames.length; i++) {
                if (annotationParamNames[i] != null)
                    parameterNames[i] = annotationParamNames[i];
            }
            for (int i = 0; i < methodParameters.length; i++) {
                methodParameters[i].setName(parameterNames[i]);
                //map for parameter name
                parameterMap.put(parameterNames[i], methodParameters[i]);
            }
        }
        return this;
    }


    @Override
    public Object invoke(ApiService apiService, Object[] parameters) throws BridgeInvokeException {
        try {
            return method.invoke(apiService.getService(), parameters);
        } catch (Exception e) {
            if (e instanceof InvocationTargetException)
                e = (Exception) ((InvocationTargetException) e).getTargetException();
            throw new BridgeInvokeException(String.format("error invoke %s.%s", apiService.getServiceName(), getName()), e);
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
