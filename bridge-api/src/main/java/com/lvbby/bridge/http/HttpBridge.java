package com.lvbby.bridge.http;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by peng on 16/9/24.
 * usage above the ApiGateWay
 */
public class HttpBridge {
    private ApiGateWay apiGateWay;
    private String paramTypeLabel = "_param_type";
    private String serviceLabel = "api";
    private String paramName = "param";

    public HttpBridge(ApiGateWay apiGateWay) {
        this.apiGateWay = apiGateWay;
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws BridgeException {
        try {
            Object re = process(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(re));
        } catch (Exception e) {
            throw new BridgeRunTimeException(e);
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object process(HttpServletRequest request) throws BridgeException {
        String serviceParam = getParameter(request, serviceLabel);
        if (StringUtils.isBlank(serviceParam))
            throw new BridgeException(String.format("service not given for param %s", serviceLabel));
        String[] split = serviceParam.trim().split("\\.");
        if (split.length != 2)
            throw new BridgeException(String.format("service format should be url?service=service.method&param=[json,json ... json]", serviceLabel));
        String service = split[0];
        String method = split[1];
        String param = getParameter(request, paramName);
        String paramType = getParameter(request, this.paramTypeLabel, "json");
        ApiService apiService = apiGateWay.getApiService(service);
        if (apiService == null)
            throw new BridgeException(String.format("can't find service for %s", service));
        List<ApiMethod> apiMethods = apiService.getApiMethods(method);
        if (apiMethods == null || apiMethods.isEmpty())
            throw new BridgeException(String.format("can't find service for %s.%s", service, method));


        Context context = null;
        if (Objects.equal(paramType, "json")) {
            int size = JSON.parseArray(param).size();
            for (ApiMethod apiMethod : apiMethods) {
                MethodParameter[] paramTypes = apiMethod.getParamTypes();
                if (size == paramTypes.length) {
                    Type[] types = new Type[paramTypes.length];
                    for (int i = 0; i < paramTypes.length; i++) {
                        types[i] = paramTypes[i].getType();
                    }
                    Object[] objects = JSON.parseArray(param, types).toArray();
                    context = new Context(service, method, Params.of(objects));
                    break;//TODO how to deal with same length methods
                }
            }
        }
        if (context == null)
            throw new BridgeException(String.format("failed to create context for %s.%s", service, method));
        return apiGateWay.proxy(context);
    }

    private String getParameter(ServletRequest request, String param) {
        return getParameter(request, param, null);
    }

    private String getParameter(ServletRequest request, String param, String defaultValue) {
        String[] parameterValues = request.getParameterValues(param);
        return parameterValues == null || parameterValues.length < 1 ? (defaultValue == null ? null : defaultValue) : parameterValues[0];
    }
}
