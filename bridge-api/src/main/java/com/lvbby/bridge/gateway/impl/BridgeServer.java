package com.lvbby.bridge.gateway.impl;

import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.api.param.parser.ParamsParserFactory;
import com.lvbby.bridge.filter.anno.DefaultFilter;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.handler.common.AnnotationResponseWrapperHandler;

/**
 * Created by lipeng on 16/11/9.
 */
public class BridgeServer extends AbstractBridge {

    private ParamsParserFactory paramsParserFactory = ParamsParserFactory.getInstance();

    {
        addApiFilter(new DefaultFilter());
        addPostHandler(new AnnotationResponseWrapperHandler());
    }

    @Override
    public ParamsParser paramsParser(Request request) {
        return paramsParserFactory.getParamsParser(request.getParamType());
    }
}
