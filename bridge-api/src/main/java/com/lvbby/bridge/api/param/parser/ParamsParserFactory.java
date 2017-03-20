package com.lvbby.bridge.api.param.parser;

import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamsParser;

import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 */
public class ParamsParserFactory {
    Map<String, ParamsParser> map = Maps.newHashMap();

    {
        addParamsParser(new MapPreciseParamsParser());
        addParamsParser(new JsonArrayParamsParser());
        addParamsParser(new JsonParamsParser());
        addParamsParser(new NormalParamsParser());
        addParamsParser(new MapsParamsParser());
    }

    public ParamsParser getParamsParser(String type) {
        return map.get(type);
    }

    public void addParamsParser(ParamsParser paramsParser) {
        if (paramsParser != null)
            map.put(paramsParser.getType(), paramsParser);
    }
}
