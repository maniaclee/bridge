package com.lvbby.bridge.api.http;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by peng on 2016/9/27.
 */
public class ServiceResponse implements Serializable {

    private static final long serialVersionUID = -4849488287125821274L;
    private boolean success = true;

    private String errorMsg;

    private Object data;


    public static <T extends ServiceResponse> T success(Class<T> tClass, Object data) {
        return create(tClass, data, true, null);
    }

    public static <T extends ServiceResponse> T error(Class<T> tClass, String errorMsg) {
        return create(tClass, null, false, errorMsg);
    }

    public static <T extends ServiceResponse> T create(Class<T> tClass, Object data, boolean isSuccess, String errorMsg) {
        try {
            T t = tClass.newInstance();
            t.setData(data);
            t.setSuccess(isSuccess);
            t.setErrorMsg(errorMsg);
            return t;
        } catch (Exception e) {
            throw new IllegalArgumentException("failed to instance : " + tClass.getName(), e);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
