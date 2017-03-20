package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.Parameters;

import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 */
public class MapsParamsParser extends AbstractParamsParser {
    private static final String type = ParamFormat.MAP.getValue();

    @Override
    public String getType() {
        return ParamFormat.MAP.getValue();
    }

    @Override
    public boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters) {
        Object arg = context.getRequest().getParam();
        return arg instanceof Map;
    }

    @Override
    public Parameters parse(ParamParsingContext context, MethodParameter[] methodParameters) {
        Map<String, String> map = (Map<String, String>) context.getRequest().getParam();
        return Parameters.ofMap(map);
    }
}
