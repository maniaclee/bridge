package com.lvbby.bridge.api.config;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.http.ServiceResponse;
import com.lvbby.bridge.api.wrapper.Context;

/**
 * Created by peng on 2016/9/27.
 */
public class ResponseWrapper implements ApiGateWayPostHandler {
    @Override
    public Object success(Context context, Object result) {
        return ServiceResponse.success(result);
    }

    /***
     * exception handling should be more delicate , this is too rough
     * you can define and error converter like http code
     * or use an aop to convert the error to an text for front-end to display
     *
     * @param context
     * @param result
     * @param e
     * @return
     * @throws BridgeException
     */
    @Override
    public Object error(Context context, Object result, Exception e) throws BridgeException {
        return ServiceResponse.error(e.getMessage());
    }
}
