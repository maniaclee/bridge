package com.lvbby.bridge.api.gateway;

import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
@Deprecated
public interface ServiceRouter {
    void init(List<ApiService> services);

    ApiService getService(String serviceName);

    List<MethodWrapper> getMethods(String serviceName, String methodName);

    MethodWrapper findMethod(Context context);
}
