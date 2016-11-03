package com.lvbby.bridge.exception.errorhandler;

import com.lvbby.bridge.exception.errorhandler.AbstractErrorHandler;
import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/11/1.
 */
public class DefaultErrorHandler extends AbstractErrorHandler {
    @Override
    public Object handleError(Request request, Object result, Exception e) throws Exception {
        throw e;
    }
}
