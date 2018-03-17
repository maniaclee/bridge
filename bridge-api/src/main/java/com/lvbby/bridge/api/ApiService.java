package com.lvbby.bridge.api;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.api.impl.ApiMethodReflectionBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * wrapper for service
 * Created by peng on 16/9/22.
 */
public class ApiService implements Serializable {
    private static final long serialVersionUID = -2447630858154384320L;

    private static ApiMethodBuilder defaultApiMethodBuilder = new ApiMethodReflectionBuilder();
    private Object service;
    private String serviceName;
    private Class  serviceClass;
    private Multimap<String, ApiMethod> methods = LinkedListMultimap.create();

    public static ApiService of(Object service, String serviceName) {
        return of(service, serviceName,service.getClass(), null);
    }

    public static ApiService of(Object service, String serviceName, Class serviceClass,ApiMethodBuilder apiMethodBuilder) {
        ApiService apiService = new ApiService();
        apiService.setService(service);
        apiService.setServiceName(serviceName);
        apiService.setServiceClass(serviceClass);
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

    public List<ApiMethod> getApiMethods(String methodName) {
        Collection<ApiMethod> elements = methods.get(methodName);
        return elements != null && !elements.isEmpty() ? Lists.newArrayList(elements) : Lists.<ApiMethod>newLinkedList();
    }

    public List<ApiMethod> getAllApiMethods() {
        return Lists.newArrayList(methods.values());
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

    public Class getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class serviceClass) {
        this.serviceClass = serviceClass;
    }
}
