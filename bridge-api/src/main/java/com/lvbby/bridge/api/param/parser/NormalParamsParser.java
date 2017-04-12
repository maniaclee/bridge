package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.*;
import com.lvbby.bridge.util.BridgeUtil;

import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/10/21.
 * simply use ParamParsingContext.getRequest().getParam() as the parameters
 */
public class NormalParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.NORMAL.getValue();
    }

    @Override
    public boolean match(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        if (arg instanceof Object[]) {
            return BridgeUtil.equalCollection(context.findRealParameters().stream().map(p -> p.getType()).collect(Collectors.toList()), BridgeUtil.getTypes((Object[]) arg));
        }
        return false;
    }

    @Override
    public Parameters doParse(ParamParsingContext context) {
        return Parameters.of(context.getApiMethod(), (Object[]) context.getRequest().getParam());
    }
}
