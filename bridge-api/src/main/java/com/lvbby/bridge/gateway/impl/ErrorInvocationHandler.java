package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.ErrorHanlder;
import com.lvbby.bridge.gateway.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lipeng on 16/10/25.
 */
public class ErrorInvocationHandler implements InvocationHandler {
    private ApiGateWay apiGateWay;
    private List<ErrorHanlder> errorHanlders;

    public ErrorInvocationHandler(ApiGateWay apiGateWay, List<ErrorHanlder> errorHanlders) {
        this.apiGateWay = apiGateWay;
        this.errorHanlders = errorHanlders;
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
            if (errorHanlders == null || errorHanlders.isEmpty() || !(e instanceof BridgeException))
                throw e;
            Request request = (Request) args[0];
            for (ErrorHanlder errorHanlder : errorHanlders) {
                re = errorHanlder.handleError(request, re, (BridgeException) e);
            }
        }
        return re;
    }
}
