package com.lvbby.bridge.http;

import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.Bridge;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lipeng on 16/9/26.
 */
public class HttpBridgeServer {

    /**
     * root url
     */
    private String rootPath = "/api";
    private int port = 8080;
    private Server server;


    public HttpBridgeServer(List services) {
        this(new Bridge().addServices(services));
    }

    public HttpBridgeServer(ApiGateWay apiGateWay) {
        if (apiGateWay == null)
            throw new BridgeRunTimeException("no bridge.");
        this.server = createServer(port, rootPath, new HttpBridge(apiGateWay));
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server createServer(int port, String rootPath, HttpBridge httpBridge) {
        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        handler.addServlet(new ServletHolder(new HttpProxyServlet(httpBridge)), rootPath);
        return server;
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
                httpBridge.process(req, response);
            } catch (BridgeException e) {
                throw new BridgeRunTimeException(e);
            }
        }
    }
}
