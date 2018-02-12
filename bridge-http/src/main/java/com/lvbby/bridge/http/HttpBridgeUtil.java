package com.lvbby.bridge.http;

import com.lvbby.bridge.gateway.BridgeContextHolder;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.BridgeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by lipeng on 16/10/30.
 */
public class HttpBridgeUtil {

    public static HttpServletRequest getHttpServletRequest(Context context) {
        return getHttpServletRequest(context.getRequest());
    }

    public static HttpServletRequest getHttpServletRequest(Request request) {
        return (HttpServletRequest) request.getAttribute(HttpBridge.EXT_HTTP_REQUEST);
    }

    public static HttpServletResponse getHttpServletResponse(Context context) {
        return (HttpServletResponse) context.getRequest().getAttribute(HttpBridge.EXT_HTTP_RESPONSE);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return getHttpServletRequest(BridgeContextHolder.get());
    }

    public static HttpBridge getHttpBridge(Request request) {
        return (HttpBridge) request.getAttribute(HttpBridge.EXT_HTTP_BRIDGE);
    }

    public static void validateSessionAttribute(String sessionKey, String requestKey,String errorMsg) {
        Context context = BridgeContextHolder.get();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null) {
            if (StringUtils.isEmpty(sessionKey))
                sessionKey = BridgeUtil.getDefaultServiceMethodName(context.getRequest());
            if (StringUtils.isEmpty(requestKey))
                requestKey = BridgeUtil.getDefaultServiceMethodName(context.getRequest());
            Validate.isTrue(httpServletRequest.getSession() != null
                    && httpServletRequest.getSession().getAttribute(sessionKey) != null
                    && httpServletRequest.getSession().getAttribute(sessionKey).equals(httpServletRequest.getParameter(requestKey)),errorMsg);
        }
    }

    public static void putSessionValue(HttpServletRequest httpServletRequest, String key, Object value) {
        if (httpServletRequest.getSession() != null)
            httpServletRequest.getSession().setAttribute(key, value);
    }

    public static String getSessionValueString(HttpServletRequest httpServletRequest, String key) {
        return Optional.of(httpServletRequest.getSession()).map(s -> s.getAttribute(key)).map(s -> s.toString()).orElse("");
    }

    public static boolean hasSessionValueString(HttpServletRequest httpServletRequest, String key, String value) {
        return Objects.equals(getSessionValueString(httpServletRequest, key), value);
    }

}
