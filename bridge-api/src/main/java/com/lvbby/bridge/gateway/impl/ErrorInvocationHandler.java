package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lipeng on 16/10/25.
 */
public class ErrorInvocationHandler implements InvocationHandler {
    private ApiGateWay apiGateWay;
    private List<ErrorHandler> errorHandlers;

    public ErrorInvocationHandler(ApiGateWay apiGateWay, List<ErrorHandler> errorHandlers) {
        this.apiGateWay = apiGateWay;
        this.errorHandlers = errorHandlers;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.getName().equals("proxy"))
            return method.invoke(apiGateWay, args);

        Object re = null;
        try {
            method.setAccessible(true);
            re = method.invoke(apiGateWay, args);
        } catch (Exception e) {
            if (errorHandlers == null || errorHandlers.isEmpty())
                throw e;
            e= (Exception) e.getCause();
            Request request = (Request) args[0];
            for (ErrorHandler errorHandler : errorHandlers) {
                re = errorHandler.handleError(request, re, e);
            }
        }
        return re;
    }
}
