package com.lvbby.bridge.api;

import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/10/21.
 */
public class ParamParsingContext {
    private Request request;
    private ApiMethod apiMethod;

    public ParamParsingContext(Request request) {
        this.request = request;
    }

    public ParamParsingContext method(ApiMethod apiMethod) {
        setApiMethod(apiMethod);
        return this;
    }

    public ApiMethod getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(ApiMethod apiMethod) {
        this.apiMethod = apiMethod;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
