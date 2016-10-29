package com.lvbby.bridge.http.filter.anno;

import com.google.common.base.Objects;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.http.HttpUserManager;
import com.lvbby.bridge.http.annotation.HttpUser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/29.
 */
public class HttpUserFilter extends AbstractHttpAnnotationFilter<HttpUser> {
    private HttpUserManager httpUserManager;

    private HttpUserFilter(HttpUserManager httpUserManager) {
        this.httpUserManager = httpUserManager;
    }

    public static HttpUserFilter of(HttpUserManager httpUserManager) {
        return new HttpUserFilter(httpUserManager);
    }

    @Override
    public boolean canVisit(Context context, HttpUser annotation, HttpServletRequest request, HttpServletResponse response) {
        if (httpUserManager == null || !annotation.loginRequired())
            return true;
        /** login require */
        if (!httpUserManager.isLogin(context, request, response))
            return false;
        /** role is not give , let it pass */
        if (StringUtils.isBlank(annotation.requireRole()))
            return true;
        return Objects.equal(httpUserManager.getUserRole(context, request, response), annotation.requireRole());
    }
}
