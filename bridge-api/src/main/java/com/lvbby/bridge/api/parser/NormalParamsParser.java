package com.lvbby.bridge.api.parser;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.gateway.Request;
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
    public boolean matchMethod(Request request, ApiMethod apiMethod) {
        Object arg = request.getArg();
        if (arg instanceof Object[]) {
            return BridgeUtil.equalCollection(BridgeUtil.getParameterTypes(apiMethod), BridgeUtil.getTypes((Object[]) arg));
        }
        return false;
    }

    @Override
    public Params parse(Request request, ApiMethod apiMethod) {
        return Params.of((Object[]) request.getArg());
    }
}
