package com.lvbby.bridge.handler.common;

import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.util.TypeCapable;

/**
 * Created by lipeng on 16/11/30.
 */
public class TypedResponseHandler<T> extends TypeCapable<T> implements ApiGateWayPostHandler {
    @Override
    public Object success(Context context, Object result) {
        return null;
    }
}
