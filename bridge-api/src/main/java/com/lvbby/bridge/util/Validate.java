package com.lvbby.bridge.util;

/**
 * Created by lipeng on 16/11/2.
 */
public class Validate {

    public static String notBlank(String s, String error, Object... obj) {
        if (s == null)
            throwEx(error, obj);
        s = s.trim();
        if (s.isEmpty())
            throw new BridgeValidateException(error);
        return s;
    }

    public static <T> T notNull(T t, String error, Object... obj) {
        if (t == null)
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

    private static void throwEx(String error, Object... obj) {
        if (obj == null || obj.length == 0)
            throw new BridgeValidateException(error);
        throw new BridgeValidateException(String.format(error, obj));
    }
}
