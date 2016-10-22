package com.lvbby.bridge.http.request;

import java.io.Serializable;

/**
 * Created by lipeng on 16/10/20.
 */
public class HttpApiRequest implements Serializable {
    private static final long serialVersionUID = -4598257872637612151L;
    private String service;
    private String method;
    private String paramType;

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
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
}
