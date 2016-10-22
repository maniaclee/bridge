package com.lvbby.bridge.api.impl;

import com.lvbby.bridge.api.ServiceNameExtractor;
import com.lvbby.bridge.exception.BridgeRunTimeException;

/**
 * Created by peng on 16/9/24.
 */
public class ClassNameServiceNameExtractor implements ServiceNameExtractor {
    @Override
    public String getServiceName(Object service) {
        Class<?> clz = service instanceof Class ? (Class<?>) service : service.getClass();
        Class<?>[] interfaces = clz.getInterfaces();
        if (interfaces.length < 1)
            return clz.getSimpleName();
        if (interfaces.length > 1)
            throw new BridgeRunTimeException(String.format("%s has multi interfaces causing confusion for choosing the service name, please use single interface, or give the service name clearly.", service.getClass().getName()));
        return interfaces[0].getSimpleName();
    }
}
