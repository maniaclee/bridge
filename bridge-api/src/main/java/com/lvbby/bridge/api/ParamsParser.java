package com.lvbby.bridge.api;

import com.lvbby.bridge.exception.BridgeRoutingException;

/**
 * Created by lipeng on 16/10/21.
 */
public interface ParamsParser {

    boolean matchMethod(ParamParsingContext context);

    void addParameter(ParamParsingContext context, MethodParameter methodParameter, Object arg);

    Object[] parse(ParamParsingContext context) throws BridgeRoutingException;

}
