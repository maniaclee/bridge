package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.*;
import com.lvbby.bridge.util.BridgeUtil;

/**
 * Created by lipeng on 16/10/21.
 * simply use ParamParsingContext.getRequest().getParam() as the parameters
 */
public class NormalParamsParser extends AbstractParamsParser {
    private static final String type = ParamFormat.NORMAL.getValue();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getParam();
        if (arg instanceof Object[]) {
            return BridgeUtil.equalCollection(BridgeUtil.getParameterTypes(methodParameters), BridgeUtil.getTypes((Object[]) arg));
        }
        return false;
    }

    @Override
    public Parameters parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        return Parameters.of((Object[]) context.getRequest().getParam());
    }
}
