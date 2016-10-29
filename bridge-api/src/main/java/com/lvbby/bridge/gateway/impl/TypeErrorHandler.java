package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.exception.BridgeInvokeException;
import com.lvbby.bridge.exception.BridgeProcessException;
import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/10/27.
 */
public abstract class TypeErrorHandler implements ErrorHandler {
    @Override
    public Object handleError(Request request, Object result, Exception e) throws BridgeException {
        if (e instanceof BridgeInvokeException)
            return handleInvokeError(request, result, (BridgeInvokeException) e);
        if (e instanceof BridgeProcessException)
            return handleProcessError(request, result, (BridgeProcessException) e);
        if (e instanceof BridgeRoutingException)
            return handleRoutingError(request, result, (BridgeRoutingException) e);
        return handleCommonError(request, result, e);
    }


    public abstract Object handleInvokeError(Request request, Object result, BridgeInvokeException e) throws BridgeException;

    public abstract Object handleProcessError(Request request, Object result, BridgeProcessException e) throws BridgeException;

    public abstract Object handleRoutingError(Request request, Object result, BridgeRoutingException e) throws BridgeException;

    public abstract Object handleCommonError(Request request, Object result, Exception e) throws BridgeException;
}