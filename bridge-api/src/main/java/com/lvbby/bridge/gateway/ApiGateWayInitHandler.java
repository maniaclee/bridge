package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.exception.BridgeRoutingException;

/**
 * Created by lipeng on 17/4/25.
 */
public interface ApiGateWayInitHandler {

    void handle(ParamParsingContext paramParsingContext) throws BridgeRoutingException;
}
