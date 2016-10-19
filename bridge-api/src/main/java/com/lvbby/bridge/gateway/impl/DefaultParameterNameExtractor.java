package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.gateway.ParameterNameExtractor;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/9/23.
 */
public class DefaultParameterNameExtractor implements ParameterNameExtractor {
    @Override
    public String[] getParameterName(Method method) {
        return new String[0];
    }
}
