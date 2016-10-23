package com.lvbby.bridge.api;

import com.lvbby.bridge.exception.BridgeInvokeException;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethod {

    String getName();

    Object invoke(ApiService apiService, Params params) throws BridgeInvokeException;

    MethodParameter[] getParamTypes();
}
