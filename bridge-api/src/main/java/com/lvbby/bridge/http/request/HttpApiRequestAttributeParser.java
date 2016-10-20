package com.lvbby.bridge.http.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/20.
 */
public class HttpApiRequestAttributeParser implements HttpApiRequestParser {

    private String serviceAttribute = "service";
    private String methodAttribute = "method";
    private String paramTypeAttribute = "paramType";

    @Override
    public HttpApiRequest parse(HttpServletRequest request) {
        HttpApiRequest re = new HttpApiRequest();
        re.setService(request.getParameter(serviceAttribute));
        re.setMethod(request.getParameter(methodAttribute));
        re.setParamType(request.getParameter(paramTypeAttribute));
        return re;
    }

    public String getServiceAttribute() {
        return serviceAttribute;
    }

    public void setServiceAttribute(String serviceAttribute) {
        this.serviceAttribute = serviceAttribute;
    }

    public String getMethodAttribute() {
        return methodAttribute;
    }

    public void setMethodAttribute(String methodAttribute) {
        this.methodAttribute = methodAttribute;
    }

    public String getParamTypeAttribute() {
        return paramTypeAttribute;
    }

    public void setParamTypeAttribute(String paramTypeAttribute) {
        this.paramTypeAttribute = paramTypeAttribute;
    }
}
