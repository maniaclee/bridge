package com.lvbby.bridge.gateway;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.exception.*;

import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 */
public abstract class AbstractBridge extends AbstractApiGateWay implements ApiGateWay, ApiServiceBuilder {
    private Map<String, ApiService> serviceMap = Maps.newHashMap();

    /***
     * @param request
     * @return
     * @throws BridgeException all BridgeException's subclasses will wrap
     */
    @Override
    public Object proxy(Request request) throws Exception {

        Object re = null;
        try {
            Context context = initContext(request);

            /** filter */
            for (ApiGateWayFilter apiGateWayFilter : filters) {
                boolean canVisit = false;
                try {
                    canVisit = apiGateWayFilter.canVisit(context);
                } catch (Exception e) {
                    throw new BridgeProcessException(String.format("%s.%s [exception] can't be visit! Blocked by %s ", request.getService(), request.getMethod(), apiGateWayFilter.getClass().getSimpleName()), e)
                            .setBridgeComponent(apiGateWayFilter)
                            .setErrorType(BridgeProcessException.Filter);
                }
                if (!canVisit)
                    throw new BridgeProcessException(String.format("%s.%s can't be visit! Blocked by %s ", request.getService(), request.getMethod(), apiGateWayFilter.getClass().getSimpleName()))
                            .setBridgeComponent(apiGateWayFilter)
                            .setErrorType(BridgeProcessException.Filter);
            }

            /** pre handlers */
            ApiGateWayPreHandler errorPreHandler = null;
            try {
                for (ApiGateWayPreHandler preHandler : preHandlers) {
                    errorPreHandler = preHandler;
                    preHandler.preProcess(context);
                }
            } catch (BridgeInterruptException ine) {
                //                return ine.getArg();TODO 不能直接return，负责后面的处理器没有走完
            } catch (Exception e) {
                throw new BridgeProcessException(e).setBridgeComponent(errorPreHandler).setErrorType(BridgeProcessException.PreProcess);
            }

            /** invoke */
            BridgeInvokeException invokeException = null;
            try {
                re = context.getApiMethod().invoke(context.getApiService(), context.getParameters());
            } catch (BridgeInvokeException e) {
                invokeException = e;
            }

            /** post handlers for success*/
            if (invokeException == null) {
                ApiGateWayPostHandler errorPostHandler = null;
                try {
                    for (ApiGateWayPostHandler postHandler : postHandlers) {
                        errorPostHandler = postHandler;
                        re = postHandler.success(context, re);
                    }
                    return re;
                } catch (Exception e) {
                    throw new BridgeProcessException(e).setBridgeComponent(errorPostHandler).setErrorType(BridgeProcessException.PostProcess);
                }
            }
            throw invokeException;
        } catch (Exception e) {
            if (errorHandler == null)
                throw e;
            e.printStackTrace();//TODO debug
            return errorHandler.handleError(request, re, e);
        }
    }

    public abstract ParamsParser paramsParser(Request request);

    /***
     * init context: find service && parsing params (including inject values)
     *
     * @param request
     * @return
     * @throws BridgeRoutingException
     */
    private Context initContext(Request request) throws BridgeRoutingException {
        /** service */
        ApiService service = serviceMap.get(request.getService());
        if (service == null)
            throw new BridgeRoutingException(String.format("service not found:%s", request.getService()));

        /** parser */
        ParamsParser paramsParser = paramsParser(request);
        if (paramsParser == null)
            throw new BridgeRoutingException(String.format("unknown param type %s", request.getParamType()));

        /** method */
        List<ApiMethod> apiMethods = service.getApiMethods(request.getMethod());
        if (apiMethods.isEmpty())
            throw new BridgeRoutingException(String.format("no method found %s", request));
        for (ApiMethod apiMethod : apiMethods) {
            ParamParsingContext paramParsingContext = new ParamParsingContext(request).method(apiMethod);

            for (ApiGateWayInitHandler apiGateWayInitHandler : getInitHandlers()) {
                apiGateWayInitHandler.handle(paramParsingContext);
            }
//            if (apiMethods.size() == 1) {
//                return buildContext(request, service, paramsParser, paramParsingContext);
//            }
            //重载时，需要先找出匹配的方法
            if (paramsParser.matchMethod(paramParsingContext))
                return buildContext(request, service, paramsParser, paramParsingContext);
        }
        throw new BridgeRoutingException(String.format("failed to route (arguments don't fit): %s", request));
    }

    private Context buildContext(Request request, ApiService service, ParamsParser paramsParser, ParamParsingContext paramParsingContext) throws BridgeRoutingException {
        /** check method name && param types|length */
        Parameters parameters = paramsParser.parse(paramParsingContext);
        if (parameters != null) {
            Context context = Context.of(request, service);
            context.setApiMethod(paramParsingContext.getApiMethod());
            context.setParameters(parameters);
            return context;
        }
        return null;
    }

    @Override
    public List<ApiService> getAllApiServices() {
        return Lists.newArrayList(serviceMap.values());
    }

    @Override
    public ApiService getApiService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public ApiGateWay addApiService(ApiService apiService) {
        serviceMap.put(apiService.getServiceName(), apiService);
        return this;
    }

    public ApiGateWay addService(Object apiService) {
        return addApiService(ApiService.of(apiService, serviceNameExtractor.getServiceName(apiService)));
    }

    public AbstractBridge addServices(final List apiService) {
        if (apiService != null) {
            for (Object service : apiService) {
                addService(service);
            }
        }
        return this;
    }


}
