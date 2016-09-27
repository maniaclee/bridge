package com.lvbby.bridge.api.route;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.api.exception.BridgeRunTimeException;
import com.lvbby.bridge.api.wrapper.ApiService;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.MethodWrapper;
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
    public ApiService getService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public List<MethodWrapper> getMethods(String serviceName, String methodName) {
        Multimap<String, MethodWrapper> methodWrapperMultimap = methodMap.get(serviceName);
        if (methodWrapperMultimap == null)
            throw new BridgeRunTimeException("service not found:" + String.valueOf(serviceName));
        Collection<MethodWrapper> re = methodWrapperMultimap.get(methodName);
        return re == null ? Lists.<MethodWrapper>newArrayList() : Lists.newArrayList(re);
    }

    @Override
    public MethodWrapper findMethod(final Context context) {
        String method = StringUtils.trimToNull(context.getMethod());
        if (StringUtils.isBlank(method))
            return null;
        Multimap<String, MethodWrapper> methodMultimap = methodMap.get(context.getServiceName());
        if (methodMultimap == null)
            return null;
        Collection<MethodWrapper> methods = methodMultimap.get(method);
        if (methods != null && methods.size() > 0) {
            if (methods.size() == 1)
                return methods.iterator().next();
            switch (context.getMethodRouter()) {
                case MethodRouter.name:
                    throw new BridgeRunTimeException(String.format("failed method[%s] with routing type:%s", method, context.getMethodRouter()));
                case MethodRouter.name_paramterNum:
                    Collection<MethodWrapper> ms = Collections2.filter(methods, new Predicate<MethodWrapper>() {
                        @Override
                        public boolean apply(MethodWrapper methodWrapper) {
                            return methodWrapper.getMethodParameters().length == context.getParam().getParams().length;
                        }
                    });
                    if (ms.size() != 1)
                        throw new BridgeRunTimeException(String.format("failed method[%s] with routing type:%s", method, context.getMethodRouter()));
                    return ms.iterator().next();
                case MethodRouter.name_paramterNum_parameterType:
                    for (MethodWrapper m : methods) {
                        if (m.match(context.getParam()))
                            return m;
                    }
                    throw new BridgeRunTimeException(String.format("failed method[%s] with routing type:%s", method, context.getMethodRouter()));
            }
        }
        return null;
    }
}
