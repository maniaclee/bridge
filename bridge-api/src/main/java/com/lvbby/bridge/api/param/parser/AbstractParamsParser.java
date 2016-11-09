package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamsParser;

/**
 * Created by lipeng on 16/11/9.
 */
public abstract class AbstractParamsParser implements ParamsParser {
    @Override
    public MethodParameter[] getMethodParameter(ApiMethod apiMethod) {
        return apiMethod.getParamTypes();
    }
}
