package com.lvbby.bridge.api.wrapper;

/**
 * Created by lipeng on 16/9/23.
 */
public class Param {
    private Object param;
    private String name;

    public Param() {
    }

    public Param(Object param) {
        this.param = param;
    }


    public Param(Object param, String name) {
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
}
