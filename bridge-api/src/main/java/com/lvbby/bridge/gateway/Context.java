package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;

/**
 * Created by lipeng on 16/10/19.
 */
public class Context {
    private Request request;
    private ApiService apiService;
    private ApiMethod apiMethod;

    public static Context of(Request request, ApiService apiService) {
        Context context = new Context();
        context.setRequest(request);
        context.setApiService(apiService);
        return context;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public ApiMethod getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(ApiMethod apiMethod) {
        this.apiMethod = apiMethod;
    }

}
