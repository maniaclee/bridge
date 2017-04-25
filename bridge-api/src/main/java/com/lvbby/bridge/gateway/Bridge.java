package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.api.param.parser.ParamsParserFactory;
import com.lvbby.bridge.filter.anno.DefaultFilter;
import com.lvbby.bridge.handler.common.AnnotationResponseWrapperHandler;

/**
 * Created by peng on 16/9/22.
 */
public class Bridge extends AbstractBridge {
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
