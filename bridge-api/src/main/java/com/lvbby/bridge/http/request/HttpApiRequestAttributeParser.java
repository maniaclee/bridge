package com.lvbby.bridge.http.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/20.
 */
public class HttpApiRequestAttributeParser implements HttpApiRequestParser {

    private String serviceParam = "service";
    private String methodParam = "method";

    @Override
    public HttpApiRequest parse(HttpServletRequest request) {
        HttpApiRequest re = new HttpApiRequest();
        re.setService(request.getParameter(serviceParam));
        re.setMethod(request.getParameter(methodParam));
        return re;
    }
}
