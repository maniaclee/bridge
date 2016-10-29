package com.lvbby.bridge.http.server;

import javax.servlet.Servlet;

/**
 * Created by lipeng on 16/10/29.
 */
public interface HttpServer {

    void start() throws Exception;

    void stop() throws Exception;

    void setPort(int port);

    void setContextPath(String contextPath);

    void addServlet(Servlet servlet, String path);

}
