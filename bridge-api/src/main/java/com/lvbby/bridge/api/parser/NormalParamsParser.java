package com.lvbby.bridge.api.parser;

import com.lvbby.bridge.api.*;
import com.lvbby.bridge.util.BridgeUtil;

/**
 * Created by lipeng on 16/10/21.
 * Map<String,String>  key -> jsonString
 */
public class NormalParamsParser implements ParamsParser {
    private static final String type = ParamFormat.NORMAL.getValue();

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getArg();
        if (arg instanceof Object[]) {
            return BridgeUtil.equalCollection(BridgeUtil.getParameterTypes(methodParameters), BridgeUtil.getTypes((Object[]) arg));
        }
        return false;
    }

    @Override
    public Params parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        return Params.of((Object[]) context.getRequest().getArg());
    }
}
