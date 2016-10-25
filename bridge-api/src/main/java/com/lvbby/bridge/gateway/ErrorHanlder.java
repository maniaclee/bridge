package com.lvbby.bridge.gateway;

import com.lvbby.bridge.exception.BridgeException;

/**
 * Created by lipeng on 16/10/24.
 */
public interface ErrorHanlder {

    Object handleError(Request request, Object result, BridgeException e) throws BridgeException;
}
