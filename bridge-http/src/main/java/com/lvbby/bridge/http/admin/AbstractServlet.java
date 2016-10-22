package com.lvbby.bridge.http.admin;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lipeng on 16/10/22.
 */
public abstract class AbstractServlet extends HttpServlet {

    private String method = "post";

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getMethod().equalsIgnoreCase(method))
            resp.setStatus(405);
        else
        /**  super.doGet done shit */
            // super.doGet(req, resp);
            handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getMethod().equalsIgnoreCase(method))
            resp.setStatus(405);
        else
            handle(req, resp);
    }

    public abstract void handle(HttpServletRequest req, HttpServletResponse response) throws IOException;

    protected void writeJson(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(object));
    }
}
