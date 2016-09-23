package com.lvbby.bridge.api.config;

import com.lvbby.bridge.api.exception.BridgeException;

/**
 * Created by peng on 16/9/23.
 */
public interface ResultHandler<T> {
    T success(Object result);

    T error(Exception e) throws BridgeException;
}
