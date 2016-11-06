package com.lvbby.bridge.exception.errorhandler;

import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/11/5.
 */
public class DebugErrorHandler extends AbstractErrorHandler {
    @Override
    public Object handleError(Request request, Object result, Exception e) throws Exception {
        e.printStackTrace();
        return handleNext(request, result, e);
    }
}
