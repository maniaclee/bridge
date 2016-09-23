package com.lvbby.bridge.api.route;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.api.wrapper.ApiService;
import com.lvbby.bridge.api.wrapper.MethodWrapper;
import com.lvbby.bridge.api.wrapper.Params;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 */
public class DefaultServiceRouter implements ServiceRouter {
    Map<String, ApiService> serviceMap = new HashMap<String, ApiService>();
    Map<String, Multimap<String, MethodWrapper>> methodMap = new HashMap();

    @Override
    public void init(List<ApiService> services) {
        for (ApiService service : services) {
            String serviceName = service.getServiceName();
            if (serviceMap.containsKey(serviceName))
                throw new IllegalArgumentException(String.format("%s already exists", serviceName));
            //services
            serviceMap.put(serviceName, service);
            //methods
            ArrayListMultimap<String, MethodWrapper> serviceHolder = ArrayListMultimap.create();
            methodMap.put(serviceName, serviceHolder);
            Method[] ms = service.getService().getClass().getDeclaredMethods();
            if (ms != null && ms.length > 0)
                for (Method m : ms) {
                    m.setAccessible(true);
                    if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
                        serviceHolder.put(m.getName(), new MethodWrapper(m).init());
                }
        }
    }

    @Override
    public ApiService findService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public MethodWrapper findMethod(ApiService service, String method, Params param) {
        method = StringUtils.trimToNull(method);
        if (service == null || StringUtils.isBlank(method))
            return null;
        Multimap<String, MethodWrapper> methodMultimap = methodMap.get(service.getServiceName());
        if (methodMultimap == null)
            return null;
        Collection<MethodWrapper> methods = methodMultimap.get(method);
        if (methods != null && methods.size() > 0) {
            if (methods.size() == 1)
                return methods.iterator().next();
            for (MethodWrapper m : methods) {
                if (m.match(param))
                    return m;
            }
        }
        return null;
    }
}
