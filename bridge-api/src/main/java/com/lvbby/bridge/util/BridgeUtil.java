package com.lvbby.bridge.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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

    public static String getDefaultServiceMethodName(Request request) {
        return request.getService() + "." + request.getMethod();
    }

    public static Exception getUserException(Exception e) {
        if (e instanceof BridgeException)
            return (Exception) e.getCause();
        return e;
    }

    public static <T> T newInstance(Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (Exception e) {
            throw new BridgeRunTimeException("failed to instance class " + clz.getName(), e);
        }
    }

    public static Object convertValueForType(Class targetClass, Object src) {
        if (src == null)
            return defaultValueForType(targetClass);
        if (targetClass.isAssignableFrom(src.getClass()))
            return src;
        if (src instanceof String) {
            String s = src.toString();
            if (StringUtils.isEmpty(s))
                return defaultValueForType(targetClass);
            if (int.class.equals(targetClass) || Integer.class.equals(targetClass))
                return Integer.parseInt(s);
            if (long.class.equals(targetClass) || Long.class.equals(targetClass))
                return Long.parseLong(s);
            if (short.class.equals(targetClass) || Short.class.equals(targetClass))
                return Short.parseShort(s);
            if (float.class.equals(targetClass) || Float.class.equals(targetClass))
                return Float.parseFloat(s);
            if (double.class.equals(targetClass) || Double.class.equals(targetClass))
                return Double.parseDouble(s);
            if (byte.class.equals(targetClass) || Byte.class.equals(targetClass))
                return Byte.parseByte(s);
            if (boolean.class.equals(targetClass) || Boolean.class.equals(targetClass))
                return Boolean.parseBoolean(s);
            if (char.class.equals(targetClass) || Character.class.equals(targetClass)) {
                org.apache.commons.lang3.Validate.isTrue(s.length() == 1);
                return s.charAt(0);
            }
            return JSON.parseObject(s, targetClass);
        }
        if (Collection.class.isAssignableFrom(targetClass)) {
            //TODO collection type, url or map
        }
        throw new IllegalArgumentException(String.format("parameter no match: %s is not match for [%s] ", src.getClass().getName(), targetClass.getName()));
    }

    public static Object defaultValueForType(Class clz) {
        if (clz.isPrimitive()) {
            if (int.class.equals(clz))
                return 0;
            if (long.class.equals(clz))
                return 0l;
            if (short.class.equals(clz))
                return 0;
            if (float.class.equals(clz))
                return 0.f;
            if (double.class.equals(clz))
                return 0.0;
            if (byte.class.equals(clz))
                return 0x0;
            if (boolean.class.equals(clz))
                return false;
            if (char.class.equals(clz)) {
                return ' ';
            }
        }
        return null;
    }

}
