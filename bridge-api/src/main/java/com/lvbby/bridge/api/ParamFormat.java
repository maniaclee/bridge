package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/10/20.
 */
public enum ParamFormat {
    Json("json"),
    JsonObject("jsonObject"),
    Map("map"),
    Array("array");
    private String value;

    ParamFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ParamFormat parse(String s) {
        for (ParamFormat paramFormat : values()) {
            if (paramFormat.value.equals(s))
                return paramFormat;
        }
        throw new IllegalArgumentException("invalid param format");
    }
}
