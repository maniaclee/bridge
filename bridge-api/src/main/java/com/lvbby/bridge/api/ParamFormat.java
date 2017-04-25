package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/10/20.
 */
public enum ParamFormat {
    Map("map"),
    Array("array");
    private String value;

    ParamFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
