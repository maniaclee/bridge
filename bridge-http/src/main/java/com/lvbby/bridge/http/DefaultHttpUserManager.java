package com.lvbby.bridge.http;

import com.lvbby.bridge.util.BridgeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;

/**
 * Created by lipeng on 17/5/2.
 */
public class DefaultHttpUserManager extends AbstractHttpUserManager {
    private String userIdPropertyName = "userId";
    private volatile Field field;

    public DefaultHttpUserManager(String sessionKey) {
        super(sessionKey);
    }

    public DefaultHttpUserManager userIdPropertyName(String userIdPropertyName) {
        this.userIdPropertyName = userIdPropertyName;
        return this;
    }

    @Override
    public String getUserId(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (session == null)
            return null;
        Object user = session.getAttribute(sessionKey);
        if (user != null) {
            if (field == null) {
                synchronized (this) {
                    if (field == null) {
                        field = BridgeUtil.getField(user.getClass(), userIdPropertyName);
                        if (field == null) {
                            throw new IllegalArgumentException("user id not found in session's user object");
                        }
                        field.setAccessible(true);
                    }
                }
            }
            try {
                Object o = field.get(user);
                if (o == null) {
                    throw new IllegalArgumentException(String.format("user id property[%s] in %s is null", userIdPropertyName, user.getClass().getName()));
                }
                return o.toString();
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(String.format("can't access user id property[%s] in %s", userIdPropertyName, user.getClass().getName()), e);
            }
        }
        return null;
    }

}
