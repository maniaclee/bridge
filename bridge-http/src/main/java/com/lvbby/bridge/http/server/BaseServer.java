package com.lvbby.bridge.http.server;

import javax.servlet.Servlet;

/**
 * Created by lipeng on 16/10/28.
 */
public class BaseServer {
    private HttpServer server = new JettyHttpServer();

    public HttpServer getServer() {
        return server;
    }

    public void setServer(HttpServer server) {
        this.server = server;
    }

    public void start() throws Exception {
        server.start();
    }

    public void setPort(int port) {
        server.setPort(port);
    }

    public void addServlet(Servlet servlet, String path) {
        server.addServlet(servlet, path);
    }

    public void setContextPath(String contextPath) {
        server.setContextPath(contextPath);
    }

    public void stop() throws Exception {
        server.stop();
    }
}
