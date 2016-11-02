package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.ServiceResponse;

/**
 * Created by lipeng on 16/10/29.
 */
public class ServiceResponseErrorHandler implements ErrorHandler {
    @Override
    public Object handleError(Request request, Object result, Exception e) throws BridgeException {
        if(e instanceof BridgeException)
            e= (Exception) e.getCause();
        return ServiceResponse.error(e.getMessage());
    }
}
