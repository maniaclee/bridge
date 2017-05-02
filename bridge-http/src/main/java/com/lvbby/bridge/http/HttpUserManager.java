package com.lvbby.bridge.http;

import com.lvbby.bridge.gateway.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/29.
 */
public interface HttpUserManager {

    boolean isLogin(Context context, HttpServletRequest request, HttpServletResponse response);

    String getUserRole(Context context, HttpServletRequest request, HttpServletResponse response);

    String getUserId(HttpServletRequest httpServletRequest);
}
