package com.lvbby.bridge.http;

import com.lvbby.bridge.gateway.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/29.
 */
public class DefaultHttpUserManager implements HttpUserManager {

    private String sessionKey;

    public DefaultHttpUserManager(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Override
    public boolean isLogin(Context context, HttpServletRequest request, HttpServletResponse response) {
        return request.getSession() != null && request.getSession().getAttribute(sessionKey) != null;
    }

    @Override
    public String getUserRole(Context context, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
