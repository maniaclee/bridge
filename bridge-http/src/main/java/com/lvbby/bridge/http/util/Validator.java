package com.lvbby.bridge.http.util;

import java.util.regex.Pattern;

/**
 * Created by peng on 16/8/22.
 */
public class Validator {
    private static final Pattern regex_email = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    private static final Pattern regex_phone = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
    private static final Pattern regex_userName = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-z][\\u4e00-\\u9fa5a-zA-Z0-9_]+$");
    private static final Pattern regex_password = Pattern.compile("[a-zA-Z0-9_#@!^&*\\-=+]+");

    public static boolean isEmail(String s) {
        return match(regex_email, s);
    }

    public static boolean isPhone(String s) {
        return match(regex_phone, s);
    }

    public static boolean isUserName(String s) {
        return match(regex_userName, s);
    }

    public static boolean isPassword(String s) {
        return match(regex_password, s);
    }


    private static boolean match(Pattern p, String s) {
        return p.matcher(s).matches();
    }

    public static void main(String[] args) {
        System.out.println(isUserName("sdfsd"));
    }
}
