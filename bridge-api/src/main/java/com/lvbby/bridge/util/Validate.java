package com.lvbby.bridge.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Function;

/**
 * Created by lipeng on 16/11/2.
 */
public class Validate {

    public static String notBlank(String s, String error, Object... obj) {
        if (s == null)
            throwEx(error, obj);
        s = s.trim();
        if (s.isEmpty())
            throwEx(error, obj);
        return s;
    }

    public static void hasNoneNull(String error, Object... obj) {
        if (ObjectUtils.firstNonNull(obj) == null)
            throwEx(error);
    }

    public static <T> T notNull(T t, String error, Object... obj) {
        if (t == null)
            throwEx(error, obj);
        return t;
    }
    public static <T,R> R notNull(T t, Function<T,R> function,String error, Object... obj) {
        if (t == null)
            throwEx(error, obj);
        return function.apply(t);
    }

    public static <T> T isNull(T t, String error, Object... obj) {
        if (t != null)
            throwEx(error, obj);
        return t;
    }

    public static void isTrue(boolean t, String error, Object... obj) {
        if (!t)
            throwEx(error, obj);
    }

    public static String isEmail(String s, String error, Object... obj) {
        if (!Validator.isEmail(s))
            throwEx(error, obj);
        return s;
    }

    public static String isPhone(String s, String error, Object... obj) {
        if (!Validator.isPhone(s))
            throwEx(error, obj);
        return s;
    }

    public static String isUserName(String s, String error, Object... obj) {
        if (!Validator.isUserName(s))
            throwEx(error, obj);
        return s;
    }

    public static String isPassword(String s, String error, Object... obj) {
        if (!Validator.isPassword(s))
            throwEx(error, obj);
        return s;
    }

    private static void throwEx(String error, Object... obj) {
        if (obj == null || obj.length == 0)
            throw new IllegalArgumentException(error);
        throw new IllegalArgumentException(String.format(error, obj));
    }
}
