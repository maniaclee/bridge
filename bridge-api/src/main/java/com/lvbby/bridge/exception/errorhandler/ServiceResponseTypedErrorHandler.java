package com.lvbby.bridge.exception.errorhandler;

import com.google.common.collect.Lists;
import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.BridgeUtil;
import com.lvbby.bridge.util.ServiceResponse;

import java.util.List;

/**
 * Created by lipeng on 16/10/29.
 */
public class ServiceResponseTypedErrorHandler extends AbstractErrorHandler {
    List<Class<? extends Exception>> echoExceptionClasses = Lists.newLinkedList();

    public static ServiceResponseTypedErrorHandler of(Class<? extends Exception>... echoExceptionClasses) {
        ServiceResponseTypedErrorHandler re = new ServiceResponseTypedErrorHandler();
        if (echoExceptionClasses != null)
            for (Class<? extends Exception> echoExceptionClass : echoExceptionClasses)
                re.echoExceptionClasses.add(echoExceptionClass);
        return re;
    }

    @Override
    public Object handleError(Request request, Object result, Exception e) throws Exception {
        if (e instanceof BridgeException) {
            if (e.getCause() != null)
                e = (Exception) e.getCause();
            for (Class<? extends Exception> ex : echoExceptionClasses)
                if (BridgeUtil.isInstance(e.getClass(), ex))
                    return ServiceResponse.error(e.getMessage());
        }
        return handleNext(request, result, e);
    }
}
