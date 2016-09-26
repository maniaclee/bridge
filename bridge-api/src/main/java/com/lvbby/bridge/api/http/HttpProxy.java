package com.lvbby.bridge.api.http;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.MethodWrapper;
import com.lvbby.bridge.api.wrapper.Params;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/24.
 */
public class HttpProxy {
    private Bridge bridge;
    private static String paramType = "type";
    private static String paramName = "param";

    public HttpProxy(Bridge bridge) {
        this.bridge = bridge;
    }

    public Object process(String service, String method, ServletRequest request) throws BridgeException {
        Map<String, String> parameterMap = request.getParameterMap();
        String param = getParameter(request, paramName);
        String paramType = getParameter(request, HttpProxy.paramType);
        if(StringUtils.isBlank(paramType))
            paramType = "json";
        List<MethodWrapper> methods = bridge.getServiceRouter().getMethods(service, method);
        MethodWrapper methodWrapper = methods.get(0);
        Context context = null;
        /** single parameter */
        if (Objects.equal(paramType, "name")) {
            Object p = JSON.parseObject(param, methodWrapper.getMethodParameters()[0].getType());
            context = new Context(service, method, Params.of(new Object[]{p}));
        } else if (Objects.equal(paramType, "json")) {
            Type[] types = new Type[methodWrapper.getMethodParameters().length];
            for (int i = 0; i < methodWrapper.getMethodParameters().length; i++) {
                types[i] = methodWrapper.getMethodParameters()[i].getType();
            }
            Object[] objects = JSON.parseArray(param, types).toArray();
            context = new Context(service, method, Params.of(objects));
        }
        return bridge.proxy(context);
    }

    private String getParameter(ServletRequest request, String param) {
        String[] parameterValues = request.getParameterValues(param);
        return parameterValues == null || parameterValues.length < 1 ? null : parameterValues[0];
    }
}
