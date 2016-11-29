package com.lvbby.bridge.util;

import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/11/29.
 */
public interface ResultConverter {

    Object convert(Context context, Object result);
}
