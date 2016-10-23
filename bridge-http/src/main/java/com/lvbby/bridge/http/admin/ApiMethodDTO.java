package com.lvbby.bridge.http.admin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lipeng on 16/10/22.
 */
public class ApiMethodDTO implements Serializable {
    private static final long serialVersionUID = -8087959704877974627L;

    private String service;
    private String name;
    private List<String> paramNames;
    private List<String> paramTypes;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }
}
