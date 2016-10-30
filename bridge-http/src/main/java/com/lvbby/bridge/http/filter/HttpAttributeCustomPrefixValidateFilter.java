package com.lvbby.bridge.http.filter;

import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal sessionAttribute of request.requestKey && session.sessionKey
 */
public class HttpAttributeCustomPrefixValidateFilter extends AbstractHttpAttributeValidateFilter {

    private final String prefix;

    public HttpAttributeCustomPrefixValidateFilter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getRequestKey(Context context) {
        return prefix + context.getApiMethod().getName();
    }

    @Override
    public String getSessionKey(Context context) {
        return getRequestKey(context);
    }
}
