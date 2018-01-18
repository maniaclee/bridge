package com.lvbby.bridge.http.parser;

import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * 从url参数里查找service和method， path?_service=XXX&_method=XXX
 * Created by lipeng on 16/10/20.
 */
public class HttpApiRequestAttributeParser extends BaseHttpApiRequestParser {

    private String serviceAttribute = "_service";
    private String methodAttribute = "_method";

    @Override
    public Request parse(HttpServletRequest request) throws BridgeRoutingException {
        Request re = super.parse(request);
        re.setService(request.getParameter(serviceAttribute));
        re.setMethod(request.getParameter(methodAttribute));
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

}
