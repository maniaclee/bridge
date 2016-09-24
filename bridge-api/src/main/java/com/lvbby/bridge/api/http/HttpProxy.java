package com.lvbby.bridge.api.http;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by peng on 16/9/24.
 */
public class HttpProxy {

    public static void sdfsdf(String url, HttpServletRequest request) {
        Map<String, String> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            
        }
    }
}
