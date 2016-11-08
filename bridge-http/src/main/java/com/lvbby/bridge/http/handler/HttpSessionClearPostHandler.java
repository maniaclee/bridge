package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.annotation.HttpSessionClear;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lipeng on 16/10/30.
 */
public class HttpSessionClearPostHandler extends HttpAnnotationPostHandler<HttpSessionClear> {

    @Override
    public Object success(Context context, HttpSessionClear httpSessionSave, Object result, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        if (session != null)
            session.invalidate();
        return result;
    }
}
