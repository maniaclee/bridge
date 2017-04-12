package com.lvbby.bridge.gateway;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipeng on 16/10/21.
 */
public class InjectProcessor {

    private static ThreadLocal injectValueMap = ThreadLocal.withInitial(() -> new HashMap<Class, Object>());

    public static void clear() {
        injectValueMap.remove();
    }

    public static Object getInjectValueByType(Type type) {
        return getInjectMap().get(type);
    }

    public static Map<Class, Object> getInjectMap() {
        return (Map<Class, Object>) injectValueMap.get();
    }

    public static boolean hasInject() {
        return getInjectMap() != null && !getInjectMap().isEmpty();
    }

    public static void inject(Class clz, Object value) {
        getInjectMap().put(clz, value);
    }

    public static boolean isInjectType(Class type) {
        return getInjectMap().containsKey(type);
    }

}
