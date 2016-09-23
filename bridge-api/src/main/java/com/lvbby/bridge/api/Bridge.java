package com.lvbby.bridge.api;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by peng on 16/9/22.
 */
public class Bridge implements ApiGateWay {
    List<ApiService> services;
    ServiceRouter serviceRouter;

    public void init() {
        if (services == null || services.isEmpty())
            throw new IllegalArgumentException("services can't be empty");
        if (serviceRouter == null)
            serviceRouter = new DefaultServiceRouter();
        serviceRouter.init(services);
    }


    @Override
    public Object proxy(Context context) throws BridgeException {
        ApiService service = serviceRouter.findService(context.getServiceName());
        if (service == null)
            throw new BridgeException(String.format("service not found:%s", context.getServiceName()));
        MethodWrapper methodWrapper = serviceRouter.findMethod(service, context.getMethod(), context.getParam());
        if (methodWrapper == null || methodWrapper.getMethod() == null)
            throw new BridgeException(String.format("method not found:%s", context.getMethod()));
        Method method = methodWrapper.getMethod();
        try {
            method.setAccessible(true);
            Object re = method.invoke(service, context.getParam());
            return re;
        } catch (Exception e) {
            throw new BridgeException(String.format("failed to execute %s.%s", service.getClass().getSimpleName(), method.getName()), e);
        }
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
