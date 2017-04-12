package com.lvbby.bridge.api.param.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.Parameters;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lipeng on 16/10/21.
 * 输入param= [{},{}]
 */
public class JsonArrayParamsParser extends AbstractParamsParser {

    @Override
    public String getType() {
        return ParamFormat.JSON_ARRAY.getValue();
    }

    @Override
    public boolean match(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        if (arg instanceof String) {
            JSONArray jsonObject = JSON.parseArray(arg.toString());
            return jsonObject.size() == context.findRealParameters().size();
        }
        return false;
    }


    @Override
    public Parameters doParse(ParamParsingContext context) {
        Object arg = context.getRequest().getParam();
        List<Object> jsonObject = JSON.parseArray(arg.toString(), context.findRealParameterTypes().toArray(new Type[0]));
        return Parameters.of(context.getApiMethod(), jsonObject.toArray());
    }

}
