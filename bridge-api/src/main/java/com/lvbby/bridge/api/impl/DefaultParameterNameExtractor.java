package com.lvbby.bridge.api.impl;

import com.lvbby.bridge.api.ParameterNameExtractor;

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
