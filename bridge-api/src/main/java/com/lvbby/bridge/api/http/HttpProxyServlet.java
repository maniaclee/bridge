package com.lvbby.bridge.api.http;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.exception.BridgeRunTimeException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by peng on 2016/9/26.
 */
public class HttpProxyServlet extends HttpServlet {
    private HttpServer httpServer;

    public HttpProxyServlet(HttpServer httpServer) {
        this.httpServer = httpServer;
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
            httpServer.httpProxy.process(req, response);
        } catch (BridgeException e) {
            throw new BridgeRunTimeException(e);
        }
    }
}
