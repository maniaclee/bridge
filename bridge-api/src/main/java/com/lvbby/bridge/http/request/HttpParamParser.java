package com.lvbby.bridge.http.request;

import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.Params;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/20.
 */
public interface HttpParamParser {
    Params parse(HttpServletRequest request, ApiService apiService, String method) throws Exception;
}
