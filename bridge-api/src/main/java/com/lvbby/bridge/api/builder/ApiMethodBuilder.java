package com.lvbby.bridge.api.builder;

import com.lvbby.bridge.api.gateway.ApiMethod;
import com.lvbby.bridge.api.gateway.ApiService;

import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethodBuilder {

    List<ApiMethod> getMethods(ApiService apiService);
}
