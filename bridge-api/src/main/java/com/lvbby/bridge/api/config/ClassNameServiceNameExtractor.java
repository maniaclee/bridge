package com.lvbby.bridge.api.config;

/**
 * Created by peng on 16/9/24.
 */
public class ClassNameServiceNameExtractor implements ServiceNameExtractor {
    @Override
    public String getServiceName(Object service) {
        return service.getClass().getInterfaces()[0].getSimpleName();
    }
}
