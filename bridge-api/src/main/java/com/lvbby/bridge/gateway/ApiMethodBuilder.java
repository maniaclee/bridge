package com.lvbby.bridge.gateway;

import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethodBuilder {

    List<ApiMethod> getMethods(ApiService apiService);
}
