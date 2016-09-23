package com.lvbby.bridge.api.wrapper;

import com.google.common.base.Objects;
import com.lvbby.bridge.api.config.ParameterNameExtrator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipeng on 16/9/23.
 * wrapper for method
 */
public class MethodWrapper {
    private Method method;
    private String name;
    private MethodParameter[] methodParameters;
    private Map<String, MethodParameter> parameterMap = new HashMap<String, MethodParameter>();

    private ParameterNameExtrator parameterNameExtrator;

    public MethodWrapper(Method method) {
        this.method = method;
        this.name = method.getName();
    }

    public void init() {
        /** parameter names */
        String[] parameterNames = parameterNameExtrator.getParameterName(method);
        if (parameterNames == null || parameterNames.length != methodParameters.length)
            throw new IllegalArgumentException("invalid method parameter length : " + method);

        Class<?>[] parameterTypes = method.getParameterTypes();
        methodParameters = new MethodParameter[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            MethodParameter methodParameter = new MethodParameter();
            methodParameter.setIndex(i);
            methodParameter.setType(parameterTypes[i]);
            methodParameter.setName(parameterNames[i]);
            methodParameters[i] = methodParameter;
            //map for parameter name
            parameterMap.put(methodParameter.getName(), methodParameter);
        }
    }


    public boolean match(Params params) {
        Param[] ps = params.getParams();
        //void
        if ((ps == null || ps.length == 0))
            return methodParameters.length == 0;

        //length must be the same at least
        if (ps.length != methodParameters.length)
            return false;

        //match by index
        if (Objects.equal(params.getType(), Params.byIndex)) {
            for (int i = 0; i < methodParameters.length; i++)
                if (!methodParameters[i].equals(methodParameters[i].getType()))
                    return false;
            return true;
        }
        //match by name
        if (Objects.equal(params.getType(), Params.byName)) {
            for (Param p : ps) {
                MethodParameter methodParameter = parameterMap.get(p.getName());
                if (methodParameter == null || Objects.equal(methodParameter.getType(), p.getParam().getClass()))
                    return false;
            }
            return true;
        }
        throw new IllegalArgumentException("unknown parameter type : " + params.getType());
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

    public ParameterNameExtrator getParameterNameExtrator() {
        return parameterNameExtrator;
    }

    public void setParameterNameExtrator(ParameterNameExtrator parameterNameExtrator) {
        this.parameterNameExtrator = parameterNameExtrator;
    }

    public static class MethodParameter {
        private int index;
        private String name;
        private Class type;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }
    }

}
