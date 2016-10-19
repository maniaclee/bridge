package com.lvbby.bridge.api.gateway;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.api.builder.ApiMethodBuilder;
import com.lvbby.bridge.api.builder.ApiMethodReflectionBuilder;

import java.util.Collection;

/**
 * wrapper for service
 * Created by peng on 16/9/22.
 */
public class ApiService {
    private static ApiMethodBuilder defaultApiMethodBuilder = new ApiMethodReflectionBuilder();
    private Object service;
    private String serviceName;
    private Multimap<String, ApiMethod> methods = LinkedListMultimap.create();

    public static ApiService of(Object service, String serviceName) {
        return of(service, serviceName, null);
    }

    public static ApiService of(Object service, String serviceName, ApiMethodBuilder apiMethodBuilder) {
        ApiService apiService = new ApiService();
        apiService.setService(service);
        apiService.setServiceName(serviceName);
        if (apiMethodBuilder == null)
            apiMethodBuilder = defaultApiMethodBuilder;
        for (ApiMethod apiMethod : apiMethodBuilder.getMethods(apiService))
            apiService.addApiMethod(apiMethod);
        return apiService;
    }

    public ApiService addApiMethod(ApiMethod apiMethod) {
        if (apiMethod != null)
            methods.put(apiMethod.getName(), apiMethod);
        return this;
    }

    public ApiMethod getApiMethod(String methodName, Params params) {
        Collection<ApiMethod> apiMethods = methods.get(methodName);
        if (apiMethods == null)
            return null;
        if (apiMethods.size() == 1)
            return apiMethods.iterator().next();
        for (ApiMethod apiMethod : apiMethods) {
            if (apiMethod.match(params))
                return apiMethod;
        }
        return null;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
