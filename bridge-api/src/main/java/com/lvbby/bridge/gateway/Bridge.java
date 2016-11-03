package com.lvbby.bridge.gateway;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.*;
import com.lvbby.bridge.api.param.parser.ParamsParserFactory;
import com.lvbby.bridge.exception.*;
import com.lvbby.bridge.filter.anno.DefaultFilter;
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
        addApiFilter(new DefaultFilter());

        /** add the default post handler */
        addPostHandler(new DefaultApiGateWayPostHandler());
    }

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
            for (ApiGateWayFilter apiGateWayFilter : apiGateWayFilters) {
                boolean canVisit = false;
                try {
                    canVisit = !apiGateWayFilter.canVisit(context);
                } catch (Exception e) {
                    throw new BridgeProcessException(String.format("%s.%s can't be visit! Blocked by %s ", request.getServiceName(), request.getMethod(), apiGateWayFilter.getClass().getSimpleName()), e)
                            .setErrorType(BridgeProcessException.Filter);
                }
                if (canVisit)
                    throw new BridgeProcessException(String.format("%s.%s can't be visit! Blocked by %s ", request.getServiceName(), request.getMethod(), apiGateWayFilter.getClass().getSimpleName()))
                            .setErrorType(BridgeProcessException.Filter);
            }

            /** pre handlers */
            try {
                for (ApiGateWayPreHandler preHandler : preHandlers)
                    preHandler.preProcess(context);
            } catch (BridgeInterruptException ine) {
                return ine.getArg();
            } catch (Exception e) {
                throw new BridgeProcessException(e).setErrorType(BridgeProcessException.PreProcess);
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
                try {
                    for (ApiGateWayPostHandler postHandler : postHandlers)
                        re = postHandler.success(context, re);
                    return re;
                } catch (Exception e) {
                    throw new BridgeProcessException(e).setErrorType(BridgeProcessException.PostProcess);
                }
            }
            throw invokeException;
        } catch (Exception e) {
            if (errorHandlers.isEmpty())
                throw e;
            for (ErrorHandler errorHandler : errorHandlers)
                re = errorHandler.handleError(request, re, e);
            return re;
        } finally {
            /** clear the inject value */
            InjectProcessor.clear();
        }
    }

    /***
     * init context: find service && parsing params (including inject values)
     *
     * @param request
     * @return
     * @throws BridgeRoutingException
     */
    private Context initContext(Request request) throws BridgeRoutingException {
        ApiService service = serviceMap.get(request.getServiceName());
        if (service == null)
            throw new BridgeRoutingException(String.format("service not found:%s", request.getServiceName()));

        ParamsParser paramsParser = paramsParserFactory.getParamsParser(request.getParamType());
        if (paramsParser == null)
            throw new BridgeRoutingException(String.format("unknown param type %s", request.getParamType()));

        ParamParsingContext paramParsingContext = new ParamParsingContext(request);

        /** find method */
        ApiMethod methodWrapper = findApiMethod(paramParsingContext, service, paramsParser);
        if (methodWrapper == null)
            throw new BridgeRoutingException(String.format("%s.%s not found for params[%s]", service.getServiceName(), request.getMethod(), JSON.toJSONString(request.getArg())));

        /** parse params , filtered by inject processor */
        Parameters parameters = request.getArg() == null ? null : paramsParser.parse(paramParsingContext, injectProcessor.filterValue(methodWrapper.getParamTypes()));
        parameters.setType(getParamType(request));//set param parsing type

        /** inject value */
        injectProcessor.injectValue(parameters, methodWrapper);

        Context context = Context.of(request, service);
        context.setApiMethod(methodWrapper);
        context.setParameters(parameters);
        return context;
    }

    private String getParamType(Request request) {
        return request.getParamType().equalsIgnoreCase(ParamFormat.JSON_ARRAY.getValue())
                || request.getParamType().equalsIgnoreCase(ParamFormat.NORMAL.getValue()) ? Parameters.byIndex : Parameters.byName;
    }

    private ApiMethod findApiMethod(ParamParsingContext request, ApiService service, ParamsParser paramsParser) {
        ApiMethod methodWrapper = null;
        List<ApiMethod> apiMethods = service.getApiMethods(request.getRequest().getMethod());
        if (apiMethods.isEmpty())
            return null;

        for (ApiMethod apiMethod : apiMethods) {
            MethodParameter[] methodParameters = injectProcessor.filterValue(apiMethod.getParamTypes());
            /** common case : if method's parameter is void */
            if (request.getRequest().getArg() == null)
                return methodParameters.length == 0 ? apiMethod : null;
            /** check method name && param types|length */
            if (paramsParser.matchMethod(request, methodParameters))
                return apiMethod;
        }
        return methodWrapper;
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
