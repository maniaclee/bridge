package com.lvbby.bridge.api.http;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.MethodWrapper;
import com.lvbby.bridge.api.wrapper.Params;

import javax.servlet.ServletRequest;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by peng on 16/9/24.
 */
public class HttpProxy {
    private Bridge bridge;
    private static String paramType = "_param_type";
    private static String paramName = "param";

    public HttpProxy(Bridge bridge) {
        this.bridge = bridge;
    }

    public Object process(String service, String method, ServletRequest request) throws BridgeException {
        String param = getParameter(request, paramName);
        String paramType = getParameter(request, HttpProxy.paramType, "json");
        List<MethodWrapper> methods = bridge.getServiceRouter().getMethods(service, method);
        if (methods == null || methods.isEmpty())
            throw new BridgeException(String.format("can't find service for %s.%s", service, method));
        if (methods.size() > 1)
            throw new BridgeException(String.format("method overload is supported in http api gateway , multi methods found for service for %s.%s", service, method));

        MethodWrapper methodWrapper = methods.get(0);
        Context context = null;
        if (Objects.equal(paramType, "json")) {
            Type[] types = new Type[methodWrapper.getMethodParameters().length];
            for (int i = 0; i < methodWrapper.getMethodParameters().length; i++) {
                types[i] = methodWrapper.getMethodParameters()[i].getType();
            }
            Object[] objects = JSON.parseArray(param, types).toArray();
            context = new Context(service, method, Params.of(objects));
        }
        if (context == null)
            throw new BridgeException(String.format("failed to create context for %s.%s", service, method));
        return bridge.proxy(context);
    }

    private String getParameter(ServletRequest request, String param) {
        return getParameter(request, param, null);
    }

    private String getParameter(ServletRequest request, String param, String defaultValue) {
        String[] parameterValues = request.getParameterValues(param);
        return parameterValues == null || parameterValues.length < 1 ? (defaultValue == null ? null : defaultValue) : parameterValues[0];
    }
}
