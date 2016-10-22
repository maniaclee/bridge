package com.lvbby.bridge.gateway;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.api.param.parser.ParamsParserFactory;
import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.filter.BlockingApiGateWayFilter;
import com.lvbby.bridge.gateway.impl.AbstractApiGateWay;
import com.lvbby.bridge.handler.DefaultApiGateWayPostHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 */
public class Bridge extends AbstractApiGateWay implements ApiGateWay, ApiServiceBuilder {
    private Map<String, ApiService> serviceMap = Maps.newHashMap();
    private ParamsParserFactory paramsParserFactory = new ParamsParserFactory();
    private InjectProcessor injectProcessor = new InjectProcessor();

    public Bridge() {
        addApiFilter(new BlockingApiGateWayFilter());

        /** add the default post handler */
        addPostHandler(new DefaultApiGateWayPostHandler());
    }


    @Override
    public void init() {
        if (serviceMap.isEmpty())
            throw new IllegalArgumentException("services can't be empty");
    }


    @Override
    public Object proxy(Request request) throws Exception {

        try {
            ApiService service = serviceMap.get(request.getServiceName());
            if (service == null)
                throw new BridgeException(String.format("service not found:%s", request.getServiceName()));

            ParamsParser paramsParser = paramsParserFactory.getParamsParser(request.getParamType());
            if (paramsParser == null)
                throw new BridgeException(String.format("unknown param type %s", request.getParamType()));

            ParamParsingContext paramParsingContext = new ParamParsingContext(request);

            /** find method */
            ApiMethod methodWrapper = findApiMethod(paramParsingContext, service, paramsParser);
            if (methodWrapper == null)
                throw new BridgeException(String.format("%s.%s not found for params[%s]", service.getServiceName(), request.getMethod(), JSON.toJSONString(request.getArg())));

            /** parse params */
            Params params = request.getArg() == null ? null : paramsParser.parse(paramParsingContext, methodWrapper.getParamTypes());


            /** inject value */
            injectProcessor.injectValue(params, methodWrapper);

            Context context = Context.of(request, service);
            context.setApiMethod(methodWrapper);
            context.setParams(params);

            /** invoke */
            Object re = null;
            try {

                /** filter */
                for (ApiGateWayFilter apiGateWayFilter : apiGateWayFilters) {
                    if (!apiGateWayFilter.canVisit(context))
                        throw new BridgeException(String.format("%s.%s can't be visit!", request.getServiceName(), request.getMethod()));
                }
                /** pre handlers */
                for (ApiGateWayPreHandler preHandler : preHandlers)
                    preHandler.preProcess(context);
                re = methodWrapper.invoke(service, params);
                /** post handlers for success*/
                for (ApiGateWayPostHandler postHandler : postHandlers)
                    re = postHandler.success(context, re);
                return re;
            } catch (Exception e) {
                /** post handlers for error */
                for (ApiGateWayPostHandler postHandler : postHandlers)
                    re = postHandler.error(context, re, e);
                return re;
            }
        } finally {
            /** clear the inject value */
            InjectProcessor.clear();
        }
    }

    private ApiMethod findApiMethod(ParamParsingContext request, ApiService service, ParamsParser paramsParser) {
        ApiMethod methodWrapper = null;
        List<ApiMethod> apiMethods = service.getApiMethods(request.getRequest().getMethod());
        if (apiMethods.isEmpty())
            methodWrapper = apiMethods.iterator().next();
        else {
            for (ApiMethod apiMethod : apiMethods) {
                if (paramsParser.matchMethod(request, injectProcessor.filterValue(apiMethod.getParamTypes()))) {
                    methodWrapper = apiMethod;
                }
                break;
            }
        }
        return methodWrapper;
    }

    @Override
    public ApiService getApiService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public Bridge addApiService(ApiService apiService) {
        serviceMap.put(apiService.getServiceName(), apiService);
        return this;
    }

    public Bridge addService(Object apiService) {
        return addApiService(ApiService.of(apiService, serviceNameExtractor.getServiceName(apiService)));
    }

    public Bridge addServices(final List apiService) {
        if (apiService != null) {
            for (Object service : apiService) {
                addService(service);
            }
        }
        return this;
    }


}
