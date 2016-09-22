package com.lvbby.bridge.api;

/**
 * Created by peng on 16/9/22.
 */
public class ApiService {
    private Object service;
    private String serviceName;

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
