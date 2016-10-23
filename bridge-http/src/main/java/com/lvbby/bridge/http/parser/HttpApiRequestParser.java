package com.lvbby.bridge.http.parser;

import com.lvbby.bridge.gateway.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/20.
 */
public interface HttpApiRequestParser {
    Request parse(HttpServletRequest request);
}
