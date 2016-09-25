package com.lvbby.bridge.api.route;

import com.lvbby.bridge.api.wrapper.ApiService;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.MethodWrapper;

import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public interface ServiceRouter {
    void init(List<ApiService> services);

    ApiService getService(String serviceName);

    List<MethodWrapper> getMethods(String serviceName, String methodName);

    MethodWrapper findMethod(Context context);
}
