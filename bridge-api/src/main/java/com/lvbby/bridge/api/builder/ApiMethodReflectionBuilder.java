package com.lvbby.bridge.api.builder;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.gateway.ApiMethod;
import com.lvbby.bridge.api.gateway.ApiService;
import com.lvbby.bridge.api.gateway.MethodWrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


/**
 * Created by peng on 16/9/22.
 */
public class ApiMethodReflectionBuilder implements ApiMethodBuilder {

    @Override
    public List<ApiMethod> getMethods(ApiService service) {
        List<ApiMethod> re = Lists.newLinkedList();
        //methods
        Method[] ms = service.getService().getClass().getDeclaredMethods();
        if (ms != null && ms.length > 0)
            for (Method m : ms) {
                m.setAccessible(true);
                if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
                    re.add(new MethodWrapper(m).init());
            }
        return re;
    }

}
