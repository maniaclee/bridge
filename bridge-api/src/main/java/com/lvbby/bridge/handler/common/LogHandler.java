package com.lvbby.bridge.handler.common;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.api.Parameters;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;
import org.apache.log4j.Logger;

import java.util.stream.Collectors;

/**
 * Created by lipeng on 2017/9/14.
 */
public class LogHandler implements ApiGateWayPreHandler {
    private Logger logger;

    public LogHandler(Logger logger) {
        this.logger = logger;
    }


    @Override
    public void preProcess(Context context) {
        logger.info(String.format("%s.%s request[%s]", context.getApiService().getServiceName(), context.getApiMethod().getName(), requestToString(context.getParameters())));
    }

    public String requestToString(Parameters parameters) {
        if (parameters.getType().equalsIgnoreCase(Parameters.byIndex)) {
            return parameters.getParameters().stream().map(parameter -> JSON.toJSONString(parameter)).collect(Collectors.joining(","));
        }
        return parameters.getParameters().stream().map(parameter -> String.format("%s:%s", parameter.getName(), JSON.toJSONString(parameter.getParam()))).collect(Collectors.joining(","));
    }
}
