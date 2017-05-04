package com.lvbby.bridge.http.web;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.util.TypeCapable;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lipeng on 2017/5/4.
 */
public abstract class AbstractHttpUserService<T> extends TypeCapable<T> implements HttpUserService<T> {
    private String userSessionKey;

    @Override
    public String encodePassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    @Override
    public String serializeUser(T user) {
        return JSON.toJSONString(user);
    }

    @Override
    public T deserializeUser(String user) {
        return JSON.parseObject(user, getType());
    }

    @Override
    public String getUserSessionKey() {
        return userSessionKey;
    }

    @Override
    public boolean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session != null && session.getAttribute(getUserSessionKey()) != null;
    }

    @Override
    public T getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null)
            return (T) session.getAttribute(getUserSessionKey());
        return null;
    }


    public void setUserSessionKey(String userSessionKey) {
        this.userSessionKey = userSessionKey;
    }
}
