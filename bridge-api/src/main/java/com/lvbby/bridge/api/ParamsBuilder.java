package com.lvbby.bridge.api;

import com.lvbby.bridge.exception.BridgeParamsParsingException;

/**
 * Created by lipeng on 16/10/20.
 */
public interface ParamsBuilder {
    Params of(Object arg) throws BridgeParamsParsingException;

    String getType();
}
