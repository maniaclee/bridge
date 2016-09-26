package com.lvbby.bridge.api.http;

import com.lvbby.bridge.api.exception.BridgeRunTimeException;
import com.lvbby.bridge.api.gateway.ApiGateWay;
import com.lvbby.bridge.api.gateway.Bridge;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.List;

/**
 * Created by lipeng on 16/9/26.
 */
public class HttpServer {

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

}
