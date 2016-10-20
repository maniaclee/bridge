package com.lvbby.bridge.http.request;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/20.
 */
public interface HttpApiRequestParser {
    HttpApiRequest parse(HttpServletRequest request);
}
