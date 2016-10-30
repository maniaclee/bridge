package com.lvbby.bridge.gateway;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.Parameter;
import com.lvbby.bridge.api.Parameters;
import com.lvbby.bridge.util.BridgeUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lipeng on 16/10/21.
 */
public class InjectProcessor {

    private static ThreadLocal<List> injectValue = new ThreadLocal<>();

    public static void setInjectValue(List value) {
        injectValue.set(value);
    }

    public static void clear() {
        injectValue.remove();
    }


    public MethodParameter[] filterValue(MethodParameter[] parameters) {
        List objects = injectValue.get();
        if (objects == null)
            return parameters;
        List<MethodParameter> re = Lists.newLinkedList();
        for (MethodParameter parameter : parameters) {
            if (!isInjectType(parameter.getType()))
                re.add(parameter);
        }
        return re.toArray(new MethodParameter[0]);
    }

    public void injectValue(Parameters parameters, ApiMethod apiMethod) {
        if (parameters == null || parameters.getParameters().length == 0 || parameters.getParameters().length == apiMethod.getParamTypes().length)
            return;
        List injects = injectValue.get();
        if (injects == null)
            return;
        Parameter[] ps = new Parameter[apiMethod.getParamTypes().length];
        //inject the value first
        for (MethodParameter methodParameter : apiMethod.getParamTypes())
            for (Object value : injects)
                if (isInjectType(value, methodParameter.getType()))
                    ps[methodParameter.getIndex()] = new Parameter(value, methodParameter.getName());
        for (int i = 0, resultIndex = 0; i < parameters.getParameters().length; i++, resultIndex++) {
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
        List list = injectValue.get();
        if (list != null)
            for (Object o : list) {
                if (BridgeUtil.isInstance(o.getClass(), type) && !type.equals(Object.class))
                    return true;
            }
        return false;
    }

}
