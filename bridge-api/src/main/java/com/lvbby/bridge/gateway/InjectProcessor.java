package com.lvbby.bridge.gateway;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.Param;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.util.BridgeUtil;

import java.lang.reflect.Type;
import java.util.LinkedList;
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
        if (injectValue.get() == null)
            return parameters;
        LinkedList<MethodParameter> re = Lists.newLinkedList();
        List<Type> parameterTypes = BridgeUtil.getTypes(injectValue.get());
        for (MethodParameter parameter : parameters) {
            //TODO subclass issue !!!!
            if (!parameterTypes.contains(parameter.getType()))
                re.add(parameter);
        }
        return re.toArray(new MethodParameter[0]);
    }

    public void injectValue(Params params, ApiMethod apiMethod) {
        if (params == null)
            return;
        List values = injectValue.get();
        if (values == null)
            return;
        Param[] ps = new Param[apiMethod.getParamTypes().length];
        //inject the value first
        for (MethodParameter methodParameter : apiMethod.getParamTypes())
            for (Object value : values)
                if (value.getClass().equals(methodParameter.getType()))
                    ps[methodParameter.getIndex()] = new Param(value, methodParameter.getName());
        for (int i = 0, resultIndex = 0; i < params.getParams().length; i++, resultIndex++) {
            while (ps[i] != null)//skip the injected value
                ++resultIndex;
            ps[resultIndex] = params.getParams()[i];
        }
        params.setParams(ps);

    }

}
