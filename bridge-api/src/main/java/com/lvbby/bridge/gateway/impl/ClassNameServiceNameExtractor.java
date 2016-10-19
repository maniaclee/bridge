package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.gateway.ServiceNameExtractor;

/**
 * Created by peng on 16/9/24.
 */
public class ClassNameServiceNameExtractor implements ServiceNameExtractor {
    @Override
    public String getServiceName(Object service) {
        Class<?> clz = service instanceof Class ? (Class<?>) service : service.getClass();
        Class<?>[] interfaces = clz.getInterfaces();
        if (interfaces.length < 1) {
            return clz.getSimpleName();
        }
        return interfaces[0].getSimpleName();
    }
}
