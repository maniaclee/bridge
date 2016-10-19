package com.lvbby.bridge.filter;

import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 * put a filter before a filter
 * if pre filter is ok , then the realApiGateFilter do the work
 */
public class FilterFilter implements ApiGateWayFilter {
    private ApiGateWayFilter pre;
    private ApiGateWayFilter realApiGateFilter;

    public FilterFilter(ApiGateWayFilter pre, ApiGateWayFilter realApiGateFilter) {
        this.pre = pre;
        this.realApiGateFilter = realApiGateFilter;
    }

    @Override
    public boolean canVisit(Context context) {
        if (pre == null || !pre.canVisit(context))
            return true;
        return realApiGateFilter.canVisit(context);
    }
}
