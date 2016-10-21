package com.lvbby.bridge.util;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.MethodParameter;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by lipeng on 16/10/21.
 */
public class BridgeUtil {
    public static List<Type> getParameterTypes(ApiMethod method) {
        List<Type> re = Lists.newArrayList();
        for (MethodParameter methodParameter : method.getParamTypes()) re.add(methodParameter.getType());
        return re;
    }
    public static List<String> getParameterNames(ApiMethod method) {
        List<String> re = Lists.newArrayList();
        for (MethodParameter methodParameter : method.getParamTypes()) re.add(methodParameter.getName());
        return re;
    }

    public static boolean equalCollection(Collection a, Collection b) {
        if (a.size() == b.size()) {
            for (Object obj : a) {
                if (!b.contains(obj))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static List<Class> getTypes(Object[] objects) {
        List<Class> re = Lists.newLinkedList();
        for (Object object : objects) re.add(object.getClass());
        return re;
    }
}
