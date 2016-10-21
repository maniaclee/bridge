package com.lvbby.bridge.api.builder;

import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.api.ParamsBuilder;
import com.lvbby.bridge.exception.BridgeParamsParsingException;

/**
 * Created by lipeng on 16/10/20.
 */
public abstract class AbstractParamsBuilder implements ParamsBuilder {

    @Override
    public Params of(Object arg) throws BridgeParamsParsingException {
        if (arg == null)
            return null;
        Params parse = parse(arg);
        if (parse != null)
            parse.setType(getType());
        return parse;
    }

    public abstract Params parse(Object arg) throws BridgeParamsParsingException;

}
