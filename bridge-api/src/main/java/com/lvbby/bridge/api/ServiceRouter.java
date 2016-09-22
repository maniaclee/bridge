package com.lvbby.bridge.api;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public interface ServiceRouter {
    void init(List<ApiService> services);

    ApiService findService(String serviceName);

    Method findMethod(ApiService service, String method, Object[] param);
}
