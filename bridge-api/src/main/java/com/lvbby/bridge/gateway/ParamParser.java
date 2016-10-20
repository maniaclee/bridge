package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.Params;

/**
 * Created by lipeng on 16/10/20.
 */
public interface ParamParser<T> {

    Params parase(ApiMethod method, ApiService apiService, T arg);

}
