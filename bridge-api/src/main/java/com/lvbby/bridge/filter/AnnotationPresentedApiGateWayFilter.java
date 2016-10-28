package com.lvbby.bridge.filter;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 * *  return true only if annotation is present && all filters match
 *
 * @see com.lvbby.bridge.annotation.Filter
 */
public class AnnotationPresentedApiGateWayFilter implements ApiGateWayFilter {
    private Class<? extends Annotation> annotation;
    private List<ApiGateWayFilter> filters = Lists.newLinkedList();

    public static AnnotationPresentedApiGateWayFilter of(Class<? extends Annotation> annotation) {
        AnnotationPresentedApiGateWayFilter re = new AnnotationPresentedApiGateWayFilter();
        re.annotation = annotation;
        return re;
    }

    public AnnotationPresentedApiGateWayFilter addFilter(ApiGateWayFilter filter) {
        this.filters.add(filter);
        return this;
    }

    @Override
    public boolean canVisit(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod && ((DefaultApiMethod) apiMethod).getMethod().getAnnotation(annotation) != null)
            for (ApiGateWayFilter filter : filters)
                if (!filter.canVisit(context))
                    return false;
        return true;
    }
}
