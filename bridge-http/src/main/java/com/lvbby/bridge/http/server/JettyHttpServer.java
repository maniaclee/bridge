package com.lvbby.bridge.http.server;

import com.google.common.collect.Lists;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import java.util.List;

/**
 * Created by lipeng on 16/10/28.
 */
public class JettyHttpServer implements HttpServer{
    private Server server;
    private int port = 7000;
    private String contextPath = "/";
    private ServletContextHandler servletContextHandler = new ServletContextHandler();
    private List<Handler> handlers = Lists.newLinkedList();


    public void start() throws Exception {
        createServer();
        server.start();
    }


    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addServlet(Servlet servlet, String path) {
        servletContextHandler.addServlet(new ServletHolder(servlet), path);
    }

    protected void createServer() {
        if (server == null) {
            server = new Server(port);
            servletContextHandler.setContextPath(contextPath);

            HandlerList handlerList = new HandlerList();
            handlerList.addHandler(servletContextHandler);
            for (Handler handler : handlers)
                handlerList.addHandler(handler);
            server.setHandler(handlerList);
        }
    }

    public void enableSession() {
        ServletContextHandler sessionServletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setSessionHandler(new SessionHandler());
        handlers.add(sessionServletContextHandler);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
