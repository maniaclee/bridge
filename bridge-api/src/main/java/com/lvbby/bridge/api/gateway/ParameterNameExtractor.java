package com.lvbby.bridge.api.gateway;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/9/23.
 */
public interface ParameterNameExtractor {
    String[] getParameterName(Method method);
}
