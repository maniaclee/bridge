package com.lvbby.bridge.util;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.MethodParameter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by lipeng on 16/10/21.
 */
public class BridgeUtil {
    public static List<Type> getTypes(Iterable objects) {
        List<Type> re = Lists.newLinkedList();
        for (Object object : objects) re.add(object.getClass());
        return re;
    }

    public static List<String> getParameterNames(MethodParameter[] methodParameters) {
        List<String> re = Lists.newArrayList();
        for (MethodParameter methodParameter : methodParameters) re.add(methodParameter.getName());
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

    public static boolean isInstance(Type test, Type dest) {
        if (test instanceof Class && dest instanceof Class) {
            return ((Class) dest).isAssignableFrom((Class<?>) test);
        }
        return false;
    }

    public static boolean contailsType(List<Type> types, Object test) {
        if (types != null)
            for (Type type : types)
                if (isInstance(test.getClass(), type))
                    return true;
        return false;
    }

    public static List<Class> getTypes(Object[] objects) {
        List<Class> re = Lists.newLinkedList();
        for (Object object : objects) re.add(object.getClass());
        return re;
    }

    public static List<Type> getParameterTypes(MethodParameter[] parameters) {
        List<Type> re = Lists.newArrayList();
        for (MethodParameter methodParameter : parameters) re.add(methodParameter.getType());
        return re;
    }

    public static Field getField(Class clz, String fieldName) {
        while (clz != null) {
            Field[] declaredFields = clz.getDeclaredFields();
            if (declaredFields != null)
                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    if (declaredField.getName().equalsIgnoreCase(fieldName))
                        return declaredField;
                }
            clz = clz.getSuperclass();
        }
        return null;
    }

}
