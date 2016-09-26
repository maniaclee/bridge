package com.lvbby.bridge.api.http;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.exception.BridgeRunTimeException;
import com.lvbby.bridge.api.gateway.ApiGateWay;
import com.lvbby.bridge.api.gateway.Bridge;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lipeng on 16/9/26.
 */
public class HttpServer {

    private String rootPath = "/api";
    private int port = 8080;
    private HttpProxy httpProxy;


    public HttpServer(List<Object> services) {
        this(new Bridge().addService(services));
    }

    public HttpServer(ApiGateWay apiGateWay) {
        if (apiGateWay == null)
            throw new BridgeRunTimeException("no bridge.");
        httpProxy = new HttpProxy(apiGateWay);
    }

    public void start() throws Exception {
        createServer(port, rootPath).start();
    }

    public Server createServer(int port, String rootPath) {
        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        handler.addServlet(HttpProxyServlet.class, rootPath);
        return server;
    }

    public class HttpProxyServlet extends HttpServlet {
        public HttpProxyServlet() {
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doGet(req, resp);
            handle(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doPost(req, resp);
            handle(req, resp);
        }

        public void handle(HttpServletRequest req, HttpServletResponse response) throws IOException {
            try {
                httpProxy.process(req, response);
            } catch (BridgeException e) {
                throw new BridgeRunTimeException(e);
            }
        }
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
