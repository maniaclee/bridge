package com.lvbby.bridge.http.session;

import org.eclipse.jetty.server.session.AbstractSessionIdManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lipeng on 16/10/29.
 */
public class RedisSessionManager  extends AbstractSessionIdManager{
    @Override
    public boolean idInUse(String s) {
        return false;
    }

    @Override
    public void addSession(HttpSession httpSession) {

    }

    @Override
    public void removeSession(HttpSession httpSession) {

    }

    @Override
    public void invalidateAll(String s) {

    }

    @Override
    public void renewSessionId(String s, String s1, HttpServletRequest httpServletRequest) {

    }
}
