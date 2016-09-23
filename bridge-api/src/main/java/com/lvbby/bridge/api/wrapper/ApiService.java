package com.lvbby.bridge.api.wrapper;

/**
 * Created by peng on 16/9/22.
 * wrapper for service
 */
public class ApiService {
    private Object service;
    private String serviceName;

    public static ApiService of(Object service) {
        return of(service, service.getClass().getSimpleName());
    }

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
