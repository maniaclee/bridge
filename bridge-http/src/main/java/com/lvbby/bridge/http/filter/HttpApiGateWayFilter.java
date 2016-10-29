package com.lvbby.bridge.http.filter;

import com.lvbby.bridge.gateway.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/28.
 */
public interface HttpApiGateWayFilter {

    boolean canVisit(Context context, HttpServletRequest request, HttpServletResponse response);

}
