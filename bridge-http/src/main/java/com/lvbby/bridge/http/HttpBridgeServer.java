package com.lvbby.bridge.http;

import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.http.server.BaseServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lipeng on 16/9/26.
 */
public class HttpBridgeServer extends BaseServer {

    /**
     * root url
     */
    private String apiPath = "/api";
    private HttpBridge httpBridge;


    public static HttpBridgeServer of(List services) {
        return of(new Bridge().addServices(services));
    }

    public static HttpBridgeServer of(ApiGateWay apiGateWay) {
        return new HttpBridgeServer(apiGateWay);
    }

    private HttpBridgeServer(ApiGateWay apiGateWay) {
        if (apiGateWay == null)
            throw new BridgeRunTimeException("no bridge.");
        this.httpBridge = HttpBridge.of(apiGateWay);
    }

    @Override
    public void start() throws Exception {
        addServlet(new HttpProxyServlet(httpBridge), apiPath);
        super.start();
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    /**
     * Created by peng on 2016/9/26.
     */
    public static class HttpProxyServlet extends HttpServlet {
        private HttpBridge httpBridge;


        public HttpProxyServlet(HttpBridge httpBridge) {
            this.httpBridge = httpBridge;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            /**  super.doGet done shit */
            // super.doGet(req, resp);
            handle(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            handle(req, resp);
        }

        public void handle(HttpServletRequest req, HttpServletResponse response) throws IOException {
            try {
                httpBridge.processBack(req, response);
            } catch (Exception e) {
                throw new IOException(e);//not good
            }
        }
    }

}
