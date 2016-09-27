package com.lvbby.bridge.api.gateway;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.lvbby.bridge.api.config.*;
import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.route.DefaultServiceRouter;
import com.lvbby.bridge.api.route.ServiceRouter;
import com.lvbby.bridge.api.wrapper.ApiService;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.MethodWrapper;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public class Bridge implements ApiGateWay {
    private List<ApiService> services = Lists.newLinkedList();
    private ServiceRouter serviceRouter;
    private ServiceNameExtractor serviceNameExtractor = new ClassNameServiceNameExtractor();
    private List<ApiGateWayPreHandler> preHandlers = Lists.newLinkedList();
    private List<ApiGateWayPostHandler> postHandlers = Lists.newArrayList();

    public Bridge() {
        /** add the default post handler */
        addPostHandler(new DefaultApiGateWayPostHandler());
    }


    @Override
    public void init() {
        if (services == null || services.isEmpty())
            throw new IllegalArgumentException("services can't be empty");
        if (serviceRouter == null)
            serviceRouter = new DefaultServiceRouter();
        serviceRouter.init(services);
    }


    @Override
    public Object proxy(Context context) throws BridgeException {
        /** pre handlers */
        for (ApiGateWayPreHandler preHandler : preHandlers)
            preHandler.preProcess(context);

        ApiService service = serviceRouter.getService(context.getServiceName());
        if (service == null)
            throw new BridgeException(String.format("service not found:%s", context.getServiceName()));
        MethodWrapper methodWrapper = serviceRouter.findMethod(context);
        if (methodWrapper == null || methodWrapper.getMethod() == null)
            throw new BridgeException(String.format("%s.%s not found for params[%s]", service.getServiceName(), context.getMethod(), context.getParam()));
        /** invoke */
        Method method = methodWrapper.getMethod();
        Object[] realParameters = methodWrapper.getRealParameters(context.getParam());
        Object re = null;
        try {
            method.setAccessible(true);
            re = method.invoke(service.getService(), realParameters);
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

    public Bridge addApiService(ApiService apiService) {
        services.add(apiService);
        return this;
    }

    public Bridge addService(Object apiService) {
        return addApiService(ApiService.of(apiService, serviceNameExtractor.getServiceName(apiService)));
    }

    public Bridge addServices(final List apiService) {
        if (apiService != null) {
            services.addAll(Collections2.transform(apiService, new Function<Object, ApiService>() {
                @Override
                public ApiService apply(Object o) {
                    return ApiService.of(o, serviceNameExtractor.getServiceName(o));
                }
            }));
        }
        return this;
    }

    public ServiceNameExtractor getServiceNameExtractor() {
        return serviceNameExtractor;
    }

    public void setServiceNameExtractor(ServiceNameExtractor serviceNameExtractor) {
        this.serviceNameExtractor = serviceNameExtractor;
    }

    public List<ApiService> getServices() {
        return services;
    }

    public void setServices(List<ApiService> services) {
        this.services = services;
    }

    public ServiceRouter getServiceRouter() {
        return serviceRouter;
    }

    @Override
    public void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler) {
        if (apiGateWayPreHandler != null)
            preHandlers.add(apiGateWayPreHandler);
    }

    @Override
    public void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler) {
        postHandlers.add(apiGateWayPostHandler);
    }


    public void setServiceRouter(ServiceRouter serviceRouter) {
        this.serviceRouter = serviceRouter;
    }
}
