package com.lvbby.bridge.api;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Method;

public class MethodParameter {
    private int index;
    private String name;
    private Class type;
    private Method method;

    /***
     * 参数类型是否匹配
     * @param object
     * @return
     */
    public boolean match(Object object){
        if(object==null)
            return true;
        return ClassUtils.isAssignable(object.getClass(),type);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}