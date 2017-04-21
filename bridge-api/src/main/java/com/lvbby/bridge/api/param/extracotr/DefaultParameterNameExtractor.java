package com.lvbby.bridge.api.param.extracotr;

import com.lvbby.bridge.api.ParameterNameExtractor;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/9/23.
 */
public class DefaultParameterNameExtractor implements ParameterNameExtractor {
    private ParameterNameExtractor  javassistParameterNameExtractor= new SpringParameterNameExtractor();

    @Override
    public String[] getParameterName(Method method) {
        return javassistParameterNameExtractor.getParameterName(method);
    }

}
