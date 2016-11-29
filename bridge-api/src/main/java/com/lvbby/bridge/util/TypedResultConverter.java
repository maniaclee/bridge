package com.lvbby.bridge.util;

import com.google.common.collect.Lists;
import com.lvbby.bridge.gateway.Context;

import java.util.Collection;
import java.util.List;

/**
 * Created by peng on 2016/9/27.
 * process the result of type T
 */
public abstract class TypedResultConverter<T> extends TypeCapable<T> implements ResultConverter {

    @Override
    public Object convert(Context context, Object result) {
        if (result == null)
            return result;
        if (isType(result)) {
            return convert(context, result);
        }
        if (result instanceof Collection) {
            List re = Lists.newLinkedList();
            ((Collection) result).forEach(o -> {
                Object convert = convert(context, result);
                if (convert != null)
                    re.add(convert);
            });
            return re;
        }
        return result;
    }

    public abstract Object doConvert(Context context, T result);

}
