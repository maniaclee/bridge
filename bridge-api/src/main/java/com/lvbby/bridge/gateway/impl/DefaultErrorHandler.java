package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/11/1.
 */
public class DefaultErrorHandler implements ErrorHandler {
    @Override
    public Object handleError(Request request, Object result, Exception e) throws BridgeException {
        if (e instanceof BridgeException)
            throw (BridgeException) e;
        throw new BridgeException(e);
    }
}
