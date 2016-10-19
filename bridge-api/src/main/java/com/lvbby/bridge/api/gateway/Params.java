package com.lvbby.bridge.api.gateway;

import java.util.Arrays;

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

    public static Params of(Object[] objects) {
        if (objects == null)
            return new Params(null);
        Param[] params = new Param[objects.length];
        for (int i = 0; i < objects.length; i++)
            params[i] = new Param(objects[i]);
        return new Params(params);
    }

    public Params() {
    }

    public Params(Param[] params) {
        this.params = params;
    }

    public Params(Param[] params, String type) {
        this.params = params;
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "Params{" +
                "params=" + Arrays.toString(params) +
                ", type='" + type + '\'' +
                '}';
    }
}
