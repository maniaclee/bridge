package com.lvbby.bridge.http.admin;

import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.gateway.impl.ServiceResponseErrorHandler;
import com.lvbby.bridge.http.HttpBridge;
import com.lvbby.bridge.http.server.BaseServer;
import com.lvbby.bridge.http.servlet.HttpBridgeDelegateServlet;

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
        httpBridge.getApiGateWay().addErrorHandler(new ServiceResponseErrorHandler());
    }


    public static void main(String[] args) throws Exception {
        AdminServer.of(new HttpBridge(new Bridge().addService(new Request()))).start();
    }

}
