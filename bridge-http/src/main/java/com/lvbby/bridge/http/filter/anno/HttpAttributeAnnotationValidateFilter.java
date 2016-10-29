package com.lvbby.bridge.http.filter.anno;

import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.annotation.HttpAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal value of request.requestKey && session.sessionKey
 */
public class HttpAttributeAnnotationValidateFilter extends AbstractHttpAnnotationFilter<HttpAttribute> {


    @Override
    public boolean canVisit(Context context, HttpAttribute annotation, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String sessionKey = annotation.sessionAttribute();
        String requestKey = annotation.requestAttribute();
        return httpServletRequest.getSession() != null
                && httpServletRequest.getSession().getAttribute(sessionKey) != null
                && httpServletRequest.getSession().getAttribute(sessionKey).equals(httpServletRequest.getAttribute(requestKey));

    }
}
