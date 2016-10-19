package com.lvbby.bridge.api.gateway;

import com.google.common.collect.Maps;
import com.lvbby.bridge.api.config.ApiGateWayPostHandler;
import com.lvbby.bridge.api.config.ApiGateWayPreHandler;
import com.lvbby.bridge.api.config.DefaultApiGateWayPostHandler;
import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.route.DefaultServiceRouter;

import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 */
public class Bridge extends AbstractApiGateWay implements ApiGateWay, ApiGateWayBuilder {
    private Map<String, ApiService> serviceMap = Maps.newHashMap();

    public Bridge() {
        /** add the default post handler */
        addPostHandler(new DefaultApiGateWayPostHandler());
    }


    @Override
    public void init() {
        if (serviceMap.isEmpty())
            throw new IllegalArgumentException("services can't be empty");
        if (serviceRouter == null)
            serviceRouter = new DefaultServiceRouter();
    }


    @Override
    public Object proxy(Context context) throws BridgeException {
        for (ApiFilter apiFilter : apiFilters) {
            if (!apiFilter.canVisit(context))
                throw new BridgeException(String.format("%s.%s can't be visit!", context.getServiceName(), context.getMethod()));
        }

        /** pre handlers */
        for (ApiGateWayPreHandler preHandler : preHandlers)
            preHandler.preProcess(context);

        ApiService service = serviceMap.get(context.getServiceName());
        if (service == null)
            throw new BridgeException(String.format("service not found:%s", context.getServiceName()));
        ApiMethod methodWrapper = service.getApiMethod(context.getMethod(), context.getParam());
        if (methodWrapper == null)
            throw new BridgeException(String.format("%s.%s not found for params[%s]", service.getServiceName(), context.getMethod(), context.getParam()));
        /** invoke */
        Object re = null;
        try {
            re = methodWrapper.invoke(service, context.getParam());
            /** post handlers for success*/
            for (ApiGateWayPostHandler postHandler : postHandlers)
                re = postHandler.success(context, re);
            return re;
        } catch (Exception e) {
            /** post handlers for error */
            for (ApiGateWayPostHandler postHandler : postHandlers)
                try {
                    re = postHandler.error(context, re, e);
                } catch (Exception e1) {
                    throw new BridgeException(e1);
                }
            return re;
        }
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
