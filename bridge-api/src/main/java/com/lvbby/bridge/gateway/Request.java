package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.Params;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 * the entrance parameter to api gateway
 */
public class Request {
    private String serviceName;
    private String method;
    private Params param;
    /***
     * extended or user custom param
     */
    private Map<String, Object> extArgs = new HashMap<String, Object>();

    public Request() {
    }

    public Request(String serviceName, String method, Params param) {
        this.serviceName = serviceName;
        this.method = method;
        this.param = param;
    }

    /***
     * add attribute for the Context to use in interceptor
     *
     * @param key
     * @param obj
     * @return
     */
    public Request addAttribute(String key, Object obj) {
        extArgs.put(key, obj);
        return this;
    }

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

    public Map<String, Object> getExtArgs() {
        return extArgs;
    }

    public void setExtArgs(Map<String, Object> extArgs) {
        this.extArgs = extArgs;
    }
}
