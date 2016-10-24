package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ApiService;
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
     * @throws Exception can be TODO
     */
    Object proxy(Request request) throws BridgeRoutingException, BridgeProcessException, BridgeInvokeException;

    List<ApiService> getAllApiServices();

    ApiService getApiService(String serviceName);

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
