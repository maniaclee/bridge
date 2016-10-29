package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.exception.BridgeInvokeException;
import com.lvbby.bridge.exception.BridgeProcessException;
import com.lvbby.bridge.exception.BridgeRoutingException;

import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    /***
     * @param request
     * @return
     * @throws BridgeRoutingException
     * @throws BridgeProcessException
     * @throws BridgeInvokeException
     */
    Object proxy(Request request) throws BridgeException;

    List<ApiService> getAllApiServices();

    ApiService getApiService(String serviceName);

    void addErrorHandler(ErrorHandler errorHandler);

    /***
     * add filter before execution, like black list / white list
     */
    void addApiFilter(ApiGateWayFilter apiGateWayFilter);

    /***
     * add handler before execution
     */
    void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler);

    /***
     * add post handler after execution,
     * result handling and error handling is pretty useful
     */
    void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler);

}
