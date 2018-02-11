package com.lvbby.bridge.handler.common;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.exception.errorhandler.AbstractErrorHandler;
import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.ApiGateWayPreHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.gateway.Request;
import org.apache.log4j.Logger;

import java.util.Arrays;
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
        logger.info(String.format("%s.%s invoke success request[%s] , response[%s]", context.getApiService().getServiceName(), context.getApiMethod().getName(), requestToString(context.getParameters()), serialize(result)));
        return result;
    }

    @Override
    public Object handleError(Request request, Object result, Exception e) throws Exception {
        logger.error(String.format("%s.%s invoke failed request[%s] ", request.getService(), request.getMethod(), serialize(request.getParam())), e);
        return result;
    }

    private String requestToString(Object[] parameters) {
        if (parameters==null || parameters.length==0) {
            return "";
        }
        return Arrays.stream(parameters).map(o -> serialize(o)).collect(Collectors.joining(","));
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
        try {
            return JSON.toJSONString(parameter);
        } catch (Exception e) {
            String format = String.format("error-serialize response[%s] ", parameter.getClass());
            logger.error(format, e);
            return format;
        }
    }

}
