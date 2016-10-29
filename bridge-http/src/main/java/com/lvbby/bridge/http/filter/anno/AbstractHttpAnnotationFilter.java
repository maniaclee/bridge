package com.lvbby.bridge.http.filter.anno;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.impl.DefaultApiMethod;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.filter.AbstractHttpApiGateWayFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;

/**
 * Created by lipeng on 16/10/28.
 */
public abstract class AbstractHttpAnnotationFilter<T extends Annotation> extends AbstractHttpApiGateWayFilter {
    @Override
    public boolean canVisit(Context context, HttpServletRequest request, HttpServletResponse response) {
        ApiMethod apiMethod = context.getApiMethod();
        if (apiMethod instanceof DefaultApiMethod) {
            T annotation = ((DefaultApiMethod) apiMethod).getMethod().getAnnotation(getType());
            if (annotation != null)
                return canVisit(context, annotation, request, response);
        }
        return true;
    }

    public Class<T> getType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
    }

    public abstract boolean canVisit(Context context, T annotation, HttpServletRequest request, HttpServletResponse response);
}
