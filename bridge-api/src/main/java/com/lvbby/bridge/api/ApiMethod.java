package com.lvbby.bridge.api;

import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.Params;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethod {

    String getName();

    boolean match(Params params);

    Object invoke(ApiService apiService, Params params) throws Exception;

    MethodParameter[] getParamTypes();
}
