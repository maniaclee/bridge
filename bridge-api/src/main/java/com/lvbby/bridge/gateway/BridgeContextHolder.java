package com.lvbby.bridge.gateway;

/**
 * Created by lipeng on 2018/2/11.
 */
public class BridgeContextHolder {

    private static ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();

    public static Context get() {
        return contextThreadLocal.get();
    }

    public static void put(Context context) {
        contextThreadLocal.set(context);
    }

    public static void clear() {
        contextThreadLocal.remove();
    }
}
