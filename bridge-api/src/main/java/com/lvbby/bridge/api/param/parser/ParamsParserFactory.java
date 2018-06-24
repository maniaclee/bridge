package com.lvbby.bridge.api.param.parser;

import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamFormat;
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
        addParamsParser(MapParamsParser.class, ParamFormat.Map.getValue());
        addParamsParser(ArrayParamsParser.class,ParamFormat.Array.getValue());
        addParamsParser(JsonParamsParser.class,ParamFormat.Json.getValue());
        addParamsParser(JsonObjectParamsParser.class,ParamFormat.JsonObject.getValue());
    }

    private ParamsParserFactory() {
    }

    public void addParamsParser(Class<? extends ParamsParser> clz,String type) {
        try {
            ParamsParser paramsParser = clz.newInstance();
            if (paramsParser != null)
                map.put(type, paramsParser);
        } catch (Exception e) {
            throw new IllegalArgumentException("error instance ParamsParser:" + clz.getName(), e);
        }
    }

    public ParamsParser getParamsParser(String type) {
        return map.get(type);
    }

}
