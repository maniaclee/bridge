package com.lvbby.bridge.api;

/**
 * Created by peng on 16/9/22.
 */
public class Context {
    private String serviceName;
    private String method;
    private Params param;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Params getParam() {
        return param;
    }

    public void setParam(Params param) {
        this.param = param;
    }
}
