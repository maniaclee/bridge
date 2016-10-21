package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethod {

    String getName();

    Object invoke(ApiService apiService, Params params) throws Exception;

    MethodParameter[] getParamTypes();
}
