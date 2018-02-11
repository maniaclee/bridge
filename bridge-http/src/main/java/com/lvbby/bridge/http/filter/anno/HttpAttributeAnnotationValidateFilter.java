package com.lvbby.bridge.http.filter.anno;

import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.annotation.HttpAttribute;
import com.lvbby.bridge.util.BridgeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal sessionAttribute of request.requestKey && session.sessionKey
 */
public class HttpAttributeAnnotationValidateFilter extends HttpAnnotationFilter<HttpAttribute> {


    @Override
    public boolean canVisit(Context context, HttpAttribute annotation, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String sessionKey = annotation.sessionAttribute();
        if (StringUtils.isEmpty(sessionKey))
            sessionKey = BridgeUtil.getDefaultServiceMethodName(context.getRequest());
        String requestKey = annotation.requestAttribute();
        if (StringUtils.isEmpty(requestKey))
            requestKey = BridgeUtil.getDefaultServiceMethodName(context.getRequest());
        return httpServletRequest.getSession() != null
                && httpServletRequest.getSession().getAttribute(sessionKey) != null
                && httpServletRequest.getSession().getAttribute(sessionKey).equals(httpServletRequest.getParameter(requestKey));
    }
}
