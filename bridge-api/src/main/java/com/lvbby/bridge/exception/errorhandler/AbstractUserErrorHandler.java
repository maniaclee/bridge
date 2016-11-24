package com.lvbby.bridge.exception.errorhandler;

import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.BridgeUtil;

/**
 * Created by lipeng on 16/11/3.
 */
public abstract class AbstractUserErrorHandler extends AbstractErrorEHandler {

    @Override
    public Object handleError(Request request, Object result, Exception e) throws Exception {
        Exception userException = BridgeUtil.getUserException(e);
        if (userException != null)
            return handleUserError(request, result, userException);
        return handleNext(request, result, e);
    }

    public abstract Object handleUserError(Request request, Object result, Exception e) throws Exception;

}
