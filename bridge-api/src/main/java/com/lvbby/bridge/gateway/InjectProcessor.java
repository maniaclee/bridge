package com.lvbby.bridge.gateway;

import java.util.List;

/**
 * Created by lipeng on 16/10/21.
 */
public class InjectProcessor {

    private static ThreadLocal<List> injectValue = new ThreadLocal<>();

    public static void setInjectValue(List value) {
        injectValue.set(value);
    }

    public static void clear() {
        injectValue.remove();
    }

    public static List getInjectValue() {
        return injectValue.get();
    }


}
