package com.lvbby.bridge.api.config;

import com.lvbby.bridge.api.exception.BridgeException;

/**
 * convert result
 * Created by peng on 16/9/23.
 */
public class DefaultResultHandler implements ResultHandler {
    @Override
    public Object success(Object result) {
        return result;
    }

    @Override
    public Object error(Exception e) throws BridgeException {
        throw new BridgeException(e);
    }
}
