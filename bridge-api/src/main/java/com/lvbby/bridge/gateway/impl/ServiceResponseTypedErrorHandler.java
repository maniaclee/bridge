package com.lvbby.bridge.gateway.impl;

import com.google.common.collect.Lists;
import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.BridgeUtil;
import com.lvbby.bridge.util.ServiceResponse;

import java.util.List;

/**
 * Created by lipeng on 16/10/29.
 */
public class ServiceResponseTypedErrorHandler implements ErrorHandler {
    List<Class<? extends Exception>> echoExceptionClasses = Lists.newLinkedList();

    public ServiceResponseTypedErrorHandler(Class<? extends Exception>... echoExceptionClasses) {
        if (echoExceptionClasses != null)
            for (Class<? extends Exception> echoExceptionClass : echoExceptionClasses)
                this.echoExceptionClasses.add(echoExceptionClass);
    }

    @Override
    public Object handleError(Request request, Object result, Exception e) throws BridgeException {
        if (e instanceof BridgeException)
            e = (Exception) e.getCause();
        for (Class<? extends Exception> ex : echoExceptionClasses) {
            if (BridgeUtil.isInstance(e.getClass(), ex))
                return ServiceResponse.error(e.getMessage());
        }
        return result;
    }
}
