package com.lvbby.bridge.gateway;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethod {

    String getName();

    boolean match(Params params);

    Object invoke(ApiService apiService, Params params) throws Exception;

    MethodParameter[] getParamTypes();
}
