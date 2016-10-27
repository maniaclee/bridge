package com.lvbby.bridge.http.admin;

import com.google.common.collect.Lists;
import com.lvbby.bridge.exception.*;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.ErrorHandler;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.gateway.impl.TypeErrorHandler;
import com.lvbby.bridge.http.HttpBridge;
import com.lvbby.bridge.http.ServiceResponse;
import com.lvbby.bridge.http.servlet.HttpBridgeDelegateServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.Servlet;

/**
 * Created by lipeng on 16/10/22.
 */
public class AdminServer {
    private Server server = new Server(7000);
    private ServletContextHandler servletContextHandler = servletContextHandler();

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

    public void addServlet(Servlet servlet, String path) {
        servletContextHandler.addServlet(new ServletHolder(servlet), path);
    }

    public void start() throws Exception {
        setHandlers(resourceHandler(), servletContextHandler, new DefaultHandler());
        server.start();
        //        server.join();
    }

    private void setHandlers(Handler... hs) {
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(hs);
        server.setHandler(handlers);
    }

    private static Handler resourceHandler() {
        ResourceHandler handler = new ResourceHandler();
        handler.setDirectoriesListed(false);
        handler.setWelcomeFiles(new String[]{"index.html"});
        handler.setBaseResource(Resource.newClassPathResource("/"));
        return handler;
    }

    private ServletContextHandler servletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/service");
        return servletContextHandler;
    }

    public static void main(String[] args) throws Exception {
        AdminServer.of(new HttpBridge(new Bridge().addService(new Request()))).start();
    }

}
