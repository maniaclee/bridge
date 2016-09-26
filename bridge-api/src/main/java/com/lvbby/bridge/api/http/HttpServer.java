package com.lvbby.bridge.api.http;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.exception.BridgeRunTimeException;
import com.lvbby.bridge.api.gateway.ApiGateWay;
import com.lvbby.bridge.api.gateway.Bridge;
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
public class HttpServer {

    /**
     * root url
     */
    private String rootPath = "/api";
    private int port = 8080;
    private Server server;


    public HttpServer(List services) {
        this(new Bridge().addServices(services));
    }

    public HttpServer(ApiGateWay apiGateWay) {
        if (apiGateWay == null)
            throw new BridgeRunTimeException("no bridge.");
        this.server = createServer(port, rootPath, new HttpProxy(apiGateWay));
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

    public Server createServer(int port, String rootPath, HttpProxy httpProxy) {
        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        handler.addServlet(new ServletHolder(new HttpProxyServlet(httpProxy)), rootPath);
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
        private HttpProxy httpProxy;


        public HttpProxyServlet(HttpProxy httpProxy) {
            this.httpProxy = httpProxy;
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
                httpProxy.process(req, response);
            } catch (BridgeException e) {
                throw new BridgeRunTimeException(e);
            }
        }
    }
}
