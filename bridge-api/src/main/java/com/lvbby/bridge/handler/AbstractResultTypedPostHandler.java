package com.lvbby.bridge.handler;

import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.util.TypeCapable;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/11/8.
 * handle result of specific type
 */
public abstract class AbstractResultTypedPostHandler<T> extends TypeCapable<T> implements ApiGateWayPostHandler {
    @Override
    public Object success(Context context, Object result) {
        if (result == null)
            return result;
        if (isType(result)) {
            return handleResult(context, (T) result);
        }
        return result;
    }

    protected boolean supportBatch(Context context, Object result) {
        return true;
    }

    protected Object handleResultBatch(Context context, Collection<T> collection) {
        return collection.stream().map(e -> handleResult(context, e)).collect(Collectors.toList());
    }

    public abstract Object handleResult(Context context, T result);
}
