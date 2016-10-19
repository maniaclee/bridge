package com.lvbby.bridge.api;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;

import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethodBuilder {

    List<ApiMethod> getMethods(ApiService apiService);
}
