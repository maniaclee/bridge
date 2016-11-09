package com.lvbby.bridge.handler;

import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/11/8.
 * convert
 */
public abstract class AbstractResultTypedConvertPostHandler<T, R> extends AbstractResultTypedPostHandler<T> {

    public Object handleResult(Context context, T result) {
        return convert(context, result);
    }

    public abstract R convert(Context context, T result);

}
