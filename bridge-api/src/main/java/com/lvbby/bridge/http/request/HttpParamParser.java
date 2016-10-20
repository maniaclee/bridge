package com.lvbby.bridge.http.request;

import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.Params;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipeng on 16/10/20.
 */
public interface HttpParamParser {
    Params parse(HttpServletRequest request, HttpServletResponse httpServletResponse, ApiService apiService, String method) throws Exception;
}
