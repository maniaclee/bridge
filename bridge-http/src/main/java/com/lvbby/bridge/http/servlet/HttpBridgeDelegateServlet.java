package com.lvbby.bridge.http.servlet;

import com.lvbby.bridge.http.HttpBridge;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lipeng on 16/10/22.
 * delegate servlet letting HttpBridge to do the work
 */
public class HttpBridgeDelegateServlet extends AbstractServlet {

    private HttpBridge httpBridge;

    public HttpBridgeDelegateServlet(HttpBridge httpBridge) {
        /***
         * use HttpBridge to admin the HttpBridge
         */
        this.httpBridge = httpBridge;
    }

    public void handle(HttpServletRequest req, HttpServletResponse response) throws IOException {
        try {
            httpBridge.processBack(req, response);
        } catch (Exception e) {
            response.setStatus(500);
            writeJson(response, e.getMessage());
        }
    }
}
