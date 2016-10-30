package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.annotation.HttpSessionSave;
import com.lvbby.bridge.serializer.Serializer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lipeng on 16/10/30.
 */
public class HttpSessionSavePostHandler extends AbstractHttpAnnotationPostHandler<HttpSessionSave> {

    @Override
    public Object success(Context context, HttpSessionSave httpSessionSave, Object result, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String value = httpSessionSave.sessionAttribute();
        if (StringUtils.isBlank(value))
            throw new BridgeRunTimeException(String.format("Can't save result to session because session key is not give. Context[%s]", context));
        HttpSession session = httpServletRequest.getSession();
        if (session == null)
            throw new BridgeRunTimeException(String.format("Can't save result to session because session do not exist. Context[%s]", context));
        try {
            Serializer serializer = httpSessionSave.serializer().newInstance();
            session.setAttribute(value, serializer.serialize(result));
        } catch (Exception e) {
            throw new BridgeRunTimeException(String.format("Can't save result to session because serializer can not be created. Serializer[%s] , Context[%s]", httpSessionSave.serializer().getName(), context));
        }
        return result;
    }
}
