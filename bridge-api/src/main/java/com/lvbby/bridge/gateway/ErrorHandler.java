package com.lvbby.bridge.gateway;

import com.lvbby.bridge.exception.BridgeException;

/**
 * Created by lipeng on 16/10/24.
 */
public interface ErrorHandler {

    Object handleError(Request request, Object result, Exception e) throws BridgeException;
}
