package com.lvbby.bridge.handler.common;

import com.lvbby.bridge.annotation.Converter;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.handler.AnnotationPostHandler;
import com.lvbby.bridge.util.BridgeUtil;

/**
 * Created by peng on 2016/9/27.
 */
public class AnnotationResponseWrapperHandler extends AnnotationPostHandler<Converter> {

    @Override
    public Object success(Context context, Converter converter, Object result) {
        if (result == null)
            return result;
        return BridgeUtil.newInstance(converter.value()).convert(context, result);
    }

}
