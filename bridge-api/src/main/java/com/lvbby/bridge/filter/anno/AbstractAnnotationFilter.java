package com.lvbby.bridge.filter.anno;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AbstractAnnotationFilter<T extends Annotation> implements ApiGateWayFilter {
    @Override
    public boolean canVisit(Context context) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            T annotation = ((DefaultApiMethod) apiMethod).getMethod().getAnnotation(getType());
            if (annotation != null)
                return canVisit(context, annotation);
        }
        return true;
    }

    public abstract boolean canVisit(Context context, T t);

    public Class<T> getType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
    }
}
