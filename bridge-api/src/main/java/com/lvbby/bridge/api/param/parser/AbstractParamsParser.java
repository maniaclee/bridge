package com.lvbby.bridge.api.param.parser;

import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.api.Parameters;
import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.InjectProcessor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lipeng on 16/11/9.
 */
public abstract class AbstractParamsParser implements ParamsParser {
    @Override
    public Parameters parse(ParamParsingContext context) throws BridgeRoutingException {
        List<MethodParameter> realParameters = context.findRealParameters();
        Object param = context.getRequest().getParam();
        if (param == null || realParameters.isEmpty())
            return Parameters.of(context.getApiMethod(), null);
        Parameters parameters = doParse(context);
        //注入参数
        if (InjectProcessor.hasInject()) {
            for (int i = 0; i < context.getApiMethod().getParamTypes().length; i++) {
                MethodParameter methodParameter = context.getApiMethod().getParamTypes()[i];
                Object injectValueByType = InjectProcessor.getInjectValueByType(methodParameter.getType());
                if (injectValueByType != null)
                    parameters.addInjectValue(i, injectValueByType);
            }
        }
        return parameters;
    }

    @Override
    public boolean matchMethod(ParamParsingContext context) {
        List<MethodParameter> realParameters = context.findRealParameters();
        Object param = context.getRequest().getParam();
        if (param == null)
            return realParameters.isEmpty();
        if (realParameters.isEmpty()) {
            if (param instanceof Collection) {
                return ((Collection) param).isEmpty();
            }
            if (param instanceof Map)
                return ((Map) param).isEmpty();
            if (param instanceof String)
                return StringUtils.isBlank((String) param);
        }
        return match(context);
    }

    protected abstract Parameters doParse(ParamParsingContext context) throws BridgeRoutingException;

    protected abstract boolean match(ParamParsingContext context);
}
