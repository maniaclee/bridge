package com.lvbby.bridge.api.gateway;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.config.DefaultResultHandler;
import com.lvbby.bridge.api.config.ResultHandler;
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
    private List<ApiService> services;
    private ServiceRouter serviceRouter;
    private ResultHandler resultHandler = new DefaultResultHandler();

    public Bridge init() {
        if (services == null || services.isEmpty())
            throw new IllegalArgumentException("services can't be empty");
        if (serviceRouter == null)
            serviceRouter = new DefaultServiceRouter();
        serviceRouter.init(services);
        return this;
    }


    @Override
    public Object proxy(Context context) throws BridgeException {
        ApiService service = serviceRouter.findService(context.getServiceName());
        if (service == null)
            throw new BridgeException(String.format("service not found:%s", context.getServiceName()));
        MethodWrapper methodWrapper = serviceRouter.findMethod(service, context.getMethod(), context.getParam());
        if (methodWrapper == null || methodWrapper.getMethod() == null)
            throw new BridgeException(String.format("%s.%s not found for params[%s]", service.getServiceName(), context.getMethod(), context.getParam()));
        /** invoke */
        Method method = methodWrapper.getMethod();
        Object[] realParameters = methodWrapper.getRealParameters(context.getParam());
        try {
            method.setAccessible(true);
            Object re = method.invoke(service.getService(), realParameters);
            return resultHandler.success(re);
        } catch (Exception e) {
            return resultHandler.error(new BridgeException(String.format("failed to execute %s.%s", service.getClass().getSimpleName(), method.getName()), e));
        }
    }

    private Object instance(Class clz) {
        try {
            return clz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public Bridge addService(ApiService apiService) {
        if (services == null)
            services = Lists.newLinkedList();
        services.add(apiService);
        return this;
    }

    public Bridge addService(Object apiService) {
        return addService(ApiService.of(apiService));
    }

    public ResultHandler getResultHandler() {
        return resultHandler;
    }

    public void setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
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

    public void setServiceRouter(ServiceRouter serviceRouter) {
        this.serviceRouter = serviceRouter;
    }
}
