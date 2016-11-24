package com.lvbby.bridge.exception.errorhandler;

import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/11/3.
 */
public abstract class AbstractErrorEHandler implements ErrorHandler {

    private ErrorHandler nextErrorHandler;

    @Override
    public ErrorHandler getNextErrorHandler() {
        return nextErrorHandler;
    }

    @Override
    public void setNextErrorHandler(ErrorHandler nextErrorHandler) {
        this.nextErrorHandler = nextErrorHandler;
    }

    public Object handleNext(Request request, Object result, Exception e) throws Exception {
        if (getNextErrorHandler() != null)
            return getNextErrorHandler().handleError(request, result, e);
        throw e;
    }

}
