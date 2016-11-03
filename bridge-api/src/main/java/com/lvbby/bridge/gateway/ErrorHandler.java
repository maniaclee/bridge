package com.lvbby.bridge.gateway;

/**
 * Created by lipeng on 16/10/24.
 */
public interface ErrorHandler {

    Object handleError(Request request, Object result, Exception e) throws Exception;

    ErrorHandler getNextErrorHandler();

    void setNextErrorHandler(ErrorHandler errorHandler);

}
