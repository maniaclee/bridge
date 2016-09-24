package com.lvbby.bridge.api.wrapper;

/**
 * wrapper for service
 * Created by peng on 16/9/22.
 */
public class ApiService {
    private Object service;
    private String serviceName;

    public static ApiService of(Object service, String serviceName) {
        ApiService apiService = new ApiService();
        apiService.setService(service);
        apiService.setServiceName(serviceName);
        return apiService;
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
