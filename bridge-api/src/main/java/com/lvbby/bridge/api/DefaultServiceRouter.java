package com.lvbby.bridge.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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
    Map<String, Multimap<String, Method>> methodMap = new HashMap();

    @Override
    public void init(List<ApiService> services) {
        for (ApiService service : services) {
            String serviceName = service.getServiceName();
            if (serviceMap.containsKey(serviceName))
                throw new IllegalArgumentException(String.format("%s already exists", serviceName));
            //services
            serviceMap.put(serviceName, service);
            //methods
            Multimap<String, Method> serviceHolder = methodMap.put(serviceName, ArrayListMultimap.<String, Method>create());
            Method[] ms = service.getClass().getDeclaredMethods();
            if (ms != null && ms.length > 0)
                for (Method m : ms) {
                    m.setAccessible(true);
                    if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
                        serviceHolder.put(m.getName(), m);
                }
        }
    }

    @Override
    public ApiService findService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public Method findMethod(ApiService service, String method, Object[] param) {
        method = StringUtils.trimToNull(method);
        if (service == null || StringUtils.isBlank(method))
            return null;
        Multimap<String, Method> methodMultimap = methodMap.get(service.getServiceName());
        if (methodMultimap == null)
            return null;
        Collection<Method> methods = methodMultimap.get(method);
        if (methods != null && methods.size() > 0) {
            if (methods.size() == 1)
                return methods.iterator().next();
            for (Method m : methods) {
                Class<?>[] parameterTypes = m.getParameterTypes();
                //void
                if ((param == null || param.length == 0) && parameterTypes.length == 0)
                    return m;
                if (parameterTypes.length != param.length)
                    continue;
                //match the method signature
                boolean match = true;
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (!parameterTypes[i].equals(param.getClass())) {
                        match = false;
                        break;
                    }
                }
                if (match)
                    return m;
            }
        }
        return null;
    }
}
