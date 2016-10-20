package com.lvbby.bridge.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/20.
 */
public class HttpContextHolder {

    private static ThreadLocal<HttpServletRequest> servletRequestThreadLocal = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> servletResponseThreadLocal = new ThreadLocal<HttpServletResponse>();


    public static void setServlet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        servletRequestThreadLocal.set(httpServletRequest);
        servletResponseThreadLocal.set(httpServletResponse);
    }


    public static HttpServletRequest getServletRequest() {
        return servletRequestThreadLocal.get();
    }

    public static HttpServletResponse getServletResponse() {
        return servletResponseThreadLocal.get();
    }

    public static void clear() {
        servletRequestThreadLocal.remove();
        servletResponseThreadLocal.remove();
    }
}
