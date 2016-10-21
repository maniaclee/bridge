package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ParamFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 * the entrance parameter to api gateway
 */
public class Request {
    private String serviceName;
    private String method;
    private Object arg;
    private String paramType = ParamFormat.JSON.getValue();
    /***
     * extended or user custom param
     */
    private Map<String, Object> extArgs = new HashMap<String, Object>();

    public Request() {
    }

    public Request(String serviceName, String method) {
        this.serviceName = serviceName;
        this.method = method;
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

    public Object getAttribute(String key) {
        return extArgs.get(key);
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
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

    public Map<String, Object> getExtArgs() {
        return extArgs;
    }

    public void setExtArgs(Map<String, Object> extArgs) {
        this.extArgs = extArgs;
    }
}
