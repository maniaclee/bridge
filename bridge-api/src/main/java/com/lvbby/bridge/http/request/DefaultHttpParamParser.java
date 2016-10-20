package com.lvbby.bridge.http.request;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.exception.BridgeException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lipeng on 16/10/20.
 */
public class DefaultHttpParamParser implements HttpParamParser {

    private String paramAttributeName;

    public DefaultHttpParamParser(String paramAttributeName) {
        this.paramAttributeName = paramAttributeName;
    }

    @Override
    public Params parse(HttpServletRequest request, ApiService apiService, String method) throws Exception {
        String parameter = request.getParameter(paramAttributeName);
        List<ApiMethod> apiMethods = apiService.getApiMethods(method);
        if (apiMethods == null || apiMethods.isEmpty())
            throw new BridgeException(String.format("can't find value for %s.%s", apiService.getServiceName(), method));
        return null;
    }
}
