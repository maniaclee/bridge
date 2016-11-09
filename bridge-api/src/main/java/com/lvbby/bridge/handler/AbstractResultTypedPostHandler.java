package com.lvbby.bridge.handler;

import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.util.TypeCapable;

/**
 * Created by lipeng on 16/11/8.
 * handle result of specific type
 */
public abstract class AbstractResultTypedPostHandler<T> extends TypeCapable<T> implements ApiGateWayPostHandler {
    @Override
    public Object success(Context context, Object result) {
        if (result != null && isType(result)) {
            return handleResult(context, (T) result);
        }
        return result;
    }

    public abstract Object handleResult(Context context, T result);

}
