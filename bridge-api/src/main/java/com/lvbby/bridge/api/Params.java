package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/9/23.
 */
public class Params {
    public static final String byIndex = "index";
    public static final String byName = "name";
    private Param[] params;
    private String type = byIndex;

    public Param[] getParams() {
        return params;
    }

    public void setParams(Param[] params) {
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
