package com.lvbby.bridge.http.parser;

import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/20.
 * extract param && paramType , let subclass to parse the service && method
 */
public class BaseHttpApiRequestParser implements HttpApiRequestParser {

    private String paramTypeAttribute = "paramType";
    private String paramAttribute = "param";

    @Override
    public Request parse(HttpServletRequest request) throws BridgeRoutingException {
        Request re = new Request();
        if (StringUtils.isNotBlank(request.getParameter(paramTypeAttribute)))
            re.setParamType(request.getParameter(paramTypeAttribute));
        re.setArg(request.getParameter(paramAttribute));
        return re;
    }


    public String getParamAttribute() {
        return paramAttribute;
    }

    public void setParamAttribute(String paramAttribute) {
        this.paramAttribute = paramAttribute;
    }

    public String getParamTypeAttribute() {
        return paramTypeAttribute;
    }

    public void setParamTypeAttribute(String paramTypeAttribute) {
        this.paramTypeAttribute = paramTypeAttribute;
    }
}
