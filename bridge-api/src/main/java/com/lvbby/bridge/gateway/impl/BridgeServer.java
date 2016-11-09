package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.api.param.parser.ParamsParserFactory;
import com.lvbby.bridge.filter.anno.DefaultFilter;
import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/11/9.
 */
public class BridgeServer extends AbstractBridge {

    private ParamsParserFactory paramsParserFactory = new ParamsParserFactory();

    {
        addApiFilter(new DefaultFilter());
    }

    @Override
    public ParamsParser paramsParser(Request request) {
        return paramsParserFactory.getParamsParser(request.getParamType());
    }
}
