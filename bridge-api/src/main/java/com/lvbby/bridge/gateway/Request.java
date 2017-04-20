package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ParamFormat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 * the entrance parameter to api gateway
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 968548429772162075L;
    private String service;
    private String method;
    private Object param;
    private String paramType = ParamFormat.MAP_WRAPPER.getValue();
    /***
     * extended or user custom param
     */
    private transient Map<String, Object> extArgs = new HashMap<String, Object>();

    public Request() {
    }

    public Request(String service, String method) {
        this.service = service;
        this.method = method;
    }

    public Request(String service, String method, Object param) {
        this.service = service;
        this.method = method;
        this.param = param;
    }

    public Request buildType(ParamFormat paramFormat) {
        setParamType(paramFormat.getValue());
        return this;
    }

    public Request buildParam(Object param) {
        setParam(param);
        return this;
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

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    @Override
    public String toString() {
        return "Request{" +
                "paramType='" + paramType + '\'' +
                ", param=" + param +
                ", method='" + method + '\'' +
                ", service='" + service + '\'' +
                '}';
    }
}
