package com.lvbby.bridge.api.param;

import com.lvbby.bridge.api.ParameterNameExtractor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by lipeng on 16/9/23.
 */
public class JdkParameterNameExtractor implements ParameterNameExtractor {
    @Override
    public String[] getParameterName(Method method) {
        Parameter[] parameters = method.getParameters();
        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            System.out.println(param.getName());
            if (!param.isNamePresent()) {
                return null;
            }
            parameterNames[i] = param.getName();
        }
        return parameterNames;
    }


}
