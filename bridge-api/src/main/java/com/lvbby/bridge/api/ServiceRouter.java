package com.lvbby.bridge.api;

import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public interface ServiceRouter {
    void init(List<ApiService> services);

    ApiService findService(String serviceName);

    MethodWrapper findMethod(ApiService service, String method, Params params);
}
