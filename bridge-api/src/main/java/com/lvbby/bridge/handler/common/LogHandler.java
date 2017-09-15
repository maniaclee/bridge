package com.lvbby.bridge.handler.common;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.api.Parameters;
import com.lvbby.bridge.exception.errorhandler.AbstractErrorHandler;
import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.gateway.Request;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

/**
 * Created by lipeng on 2017/9/14.
 */
public class LogHandler extends AbstractErrorHandler implements ApiGateWayPreHandler, ApiGateWayPostHandler {
    private Logger logger;

    public LogHandler(Logger logger) {
        this.logger = logger;
    }


    @Override
    public void preProcess(Context context) {
        logger.info(String.format("%s.%s prepare : request[%s]", context.getApiService().getServiceName(), context.getApiMethod().getName(), requestToString(context.getParameters())));
    }

    @Override
    public Object success(Context context, Object result) {
        logger.info(String.format("%s.%s invoke success request[%s] , response[%s]", context.getApiService().getServiceName(), context.getApiMethod().getName(), requestToString(context.getParameters()), doSerialize(result)));
        return result;
    }

    @Override
    public Object handleError(Request request, Object result, Exception e) throws Exception {
        logger.error(String.format("%s.%s invoke failed request[%s] ", request.getService(), request.getMethod(), doSerialize(request.getParam())), e);
        return result;
    }

    public String requestToString(Parameters parameters) {
        if (CollectionUtils.isEmpty(parameters.getParameters())) {
            return "";
        }
        if (parameters.getType().equalsIgnoreCase(Parameters.byIndex)) {
            return parameters.getParameters().stream().map(parameter -> serialize(parameter)).collect(Collectors.joining(","));
        }
        return parameters.getParameters().stream().map(parameter -> String.format("%s:%s", parameter.getName(), serialize(parameter.getParam()))).collect(Collectors.joining(","));
    }

    private String serialize(Object parameter) {
        if (parameter == null) {
            return "null";
        }
        if (parameter.getClass().isPrimitive()) {
            return parameter.toString();
        }
        return doSerialize(parameter);
    }

    public String doSerialize(Object parameter) {
        return JSON.toJSONString(parameter);
    }

}
