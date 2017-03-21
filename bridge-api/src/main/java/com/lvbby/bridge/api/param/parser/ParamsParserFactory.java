package com.lvbby.bridge.api.param.parser;

import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamsParser;

import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 */
public class ParamsParserFactory {

    private static final ParamsParserFactory instance = new ParamsParserFactory();

    public static ParamsParserFactory getInstance() {
        return instance;
    }

    Map<String, ParamsParser> map = Maps.newHashMap();

    {
        addParamsParser(MapWrapperParamsParser.class);
        addParamsParser(MapParamsParser.class);
        addParamsParser(MapPreciseParamsParser.class);
        
        addParamsParser(JsonArrayParamsParser.class);
        addParamsParser(JsonParamsParser.class);
        addParamsParser(NormalParamsParser.class);
    }

    private ParamsParserFactory() {
    }

    private void addParamsParser(Class<? extends ParamsParser> clz) {
        try {
            addParamsParser(clz.newInstance());
        } catch (Exception e) {
            throw new IllegalArgumentException("error instance ParamsParser:" + clz.getName(), e);
        }
    }

    public ParamsParser getParamsParser(String type) {
        return map.get(type);
    }

    public void addParamsParser(ParamsParser paramsParser) {
        if (paramsParser != null)
            map.put(paramsParser.getType(), paramsParser);
    }
}
