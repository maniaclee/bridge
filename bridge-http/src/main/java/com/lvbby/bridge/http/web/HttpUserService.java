package com.lvbby.bridge.http.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 2017/5/4.
 */
public interface HttpUserService<T> {
    String encodePassword(String password);

    String serializeUser(T user);

    T deserializeUser(String user);

    String getPassword(T user);

    String getName(T user);

    T queryUser(String name);

    String getUserSessionKey();

    boolean isLogin(HttpServletRequest request);

    T getUser(HttpServletRequest request);

    String getUserRole(T user);

    String getUserId(T user);
}
