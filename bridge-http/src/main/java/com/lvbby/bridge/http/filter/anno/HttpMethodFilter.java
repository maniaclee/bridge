package com.lvbby.bridge.http.filter.anno;

import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.annotation.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/19.
 * http request must have equal sessionAttribute of request.requestKey && session.sessionKey
 */
public class HttpMethodFilter extends HttpAnnotationFilter<HttpMethod> {

    @Override
    public boolean canVisit(Context context, HttpMethod annotation, HttpServletRequest request, HttpServletResponse response) {
        return request.getMethod().equalsIgnoreCase(annotation.value());
    }
}
