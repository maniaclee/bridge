package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal value of request.requestKey && session.sessionKey
 */
public class HttpAttributeFixValidateFilter extends AbstractHttpAttributeValidateFilter {

    private final String sessionKey;
    private final String requestKey;

    public HttpAttributeFixValidateFilter(String sessionKey, String requestKey) {
        this.sessionKey = sessionKey;
        this.requestKey = requestKey;
    }

    @Override
    public String getRequestKey(Context context) {
        return requestKey;
    }

    @Override
    public String getSessionKey(Context context) {
        return sessionKey;
    }
}
