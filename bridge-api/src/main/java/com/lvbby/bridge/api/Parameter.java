package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/9/23.
 */
public class Parameter {
    private Object param;
    private String name;

    public Parameter() {
    }

    public Parameter(Object param) {
        this.param = param;
    }


    public Parameter(Object param, String name) {
        this.param = param;
        this.name = name;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(param);
    }
}
