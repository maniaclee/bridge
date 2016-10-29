package com.lvbby.bridge.http;

import com.lvbby.bridge.gateway.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/30.
 */
public class HttpBridgeUtil {

    public static HttpServletRequest getHttpServletRequest(Context context) {
        return (HttpServletRequest) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_REQUEST);
    }

    public static HttpServletResponse getHttpServletResponse(Context context) {
        return (HttpServletResponse) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_RESPONSE);
    }


}
