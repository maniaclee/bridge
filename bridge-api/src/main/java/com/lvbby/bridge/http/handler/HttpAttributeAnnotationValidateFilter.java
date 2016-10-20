package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.annotation.HttpAttributeCheck;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal value of request.requestKey && session.sessionKey
 */
public class HttpAttributeAnnotationValidateFilter extends AbstractHttpAttributeValidateFilter {

    @Override
    public String getRequestKey(Context context) {
        return ((DefaultApiMethod) context.getApiMethod()).getMethod().getAnnotation(HttpAttributeCheck.class).requestAttribute();
    }

    @Override
    public String getSessionKey(Context context) {
        return ((DefaultApiMethod) context.getApiMethod()).getMethod().getAnnotation(HttpAttributeCheck.class).sessionAttribute();
    }
}
