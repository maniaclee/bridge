package com.lvbby.bridge.http.admin;

import com.google.common.collect.Lists;
import com.lvbby.bridge.exception.*;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.gateway.impl.TypeErrorHandler;
import com.lvbby.bridge.http.BaseServer;
import com.lvbby.bridge.http.HttpBridge;
import com.lvbby.bridge.http.servlet.HttpBridgeDelegateServlet;
import com.lvbby.bridge.util.ServiceResponse;

/**
 * Created by lipeng on 16/10/22.
 */
public class AdminServer extends BaseServer {

    public static AdminServer of(HttpBridge httpBridge) {
        return new AdminServer(httpBridge);
    }

    private AdminServer(HttpBridge httpBridge) {
        if (httpBridge == null)
            throw new BridgeRunTimeException("httpBridge can't be null");

        addServlet(new HttpBridgeDelegateServlet(httpBridge), "/api");
        /***
         * use HttpBridge to admin the HttpBridge!
         */
        addServlet(new HttpBridgeDelegateServlet(
                new HttpBridge(
                        new Bridge().addService(
                                new HttpBridgeService(httpBridge.getApiGateWay())))), "/admin");
        ErrorHandler errorHandler = new TypeErrorHandler() {
            @Override
            public Object handleInvokeError(Request request, Object result, BridgeInvokeException e) throws BridgeException {
                return ServiceResponse.error(e.getMessage());
            }

            @Override
            public Object handleProcessError(Request request, Object result, BridgeProcessException e) throws BridgeException {
                return ServiceResponse.error(e.getMessage());

            }

            @Override
            public Object handleRoutingError(Request request, Object result, BridgeRoutingException e) throws BridgeException {
                return ServiceResponse.error(e.getMessage());

            }

            @Override
            public Object handleCommonError(Request request, Object result, Exception e) throws BridgeException {
                return ServiceResponse.error(e.getMessage());
            }
        };
        httpBridge.setApiGateWay(httpBridge.getApiGateWay().withErrorHandler(Lists.newArrayList(errorHandler)));
    }


    public static void main(String[] args) throws Exception {
        AdminServer.of(new HttpBridge(new Bridge().addService(new Request()))).start();
    }

}
