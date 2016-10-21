package com.lvbby.bridge.api;

import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/10/21.
 */
public class ParamParsingContext {
    private Request request;

    public ParamParsingContext(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
