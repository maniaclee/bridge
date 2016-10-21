package com.lvbby.bridge.api;

import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/10/21.
 */
public interface ParamsParser {

    String getType();

    boolean matchMethod(Request request, ApiMethod apiMethod);

    Params parse(Request request, ApiMethod apiMethod);
}
