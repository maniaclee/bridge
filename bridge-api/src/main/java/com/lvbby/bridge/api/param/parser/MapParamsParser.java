package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.Parameters;

import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 * 输入param= Map , 最大可能的匹配
 */
public class MapParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.MAP.getValue();
    }

    @Override
    public boolean match(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        return arg instanceof Map;
    }

    @Override
    public Parameters doParse(ParamParsingContext context) {
        return Parameters.ofMap(context.getApiMethod(), (Map) context.getRequest().getParam());
    }

}
