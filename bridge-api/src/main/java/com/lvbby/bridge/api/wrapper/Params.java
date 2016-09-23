package com.lvbby.bridge.api.wrapper;

/**
 * Created by lipeng on 16/9/23.
 * wrapper for parameters
 */
public class Params {
    public static final String byIndex = "index";
    public static final String byName = "name";
    private Param[] params;
    /***
     * route type for finding the parameter
     */
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