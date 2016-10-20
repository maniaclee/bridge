package com.lvbby.bridge.filter;

import com.lvbby.bridge.annotation.Filter;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/10/19.
 * * an annotation filter for blocking api service by Filter
 */
public class BlockingApiGateWayFilter implements ApiGateWayFilter {

    @Override
    public boolean canVisit(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            Method method = ((DefaultApiMethod) apiMethod).getMethod();
            Filter annotation = method.getAnnotation(Filter.class);
            return annotation == null || annotation.pass();
        }
        return true;
    }
}
