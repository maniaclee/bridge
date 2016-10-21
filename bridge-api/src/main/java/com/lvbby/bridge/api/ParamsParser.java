package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/10/21.
 */
public interface ParamsParser {

    String getType();

    boolean matchMethod(ParamParsingContext context, MethodParameter[] methodParameters);

    Params parse(ParamParsingContext context, MethodParameter[] methodParameters);
}
