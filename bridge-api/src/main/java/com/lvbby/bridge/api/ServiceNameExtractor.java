package com.lvbby.bridge.api;

/**
 * Created by peng on 16/9/22.
 */
public interface ServiceNameExtractor {

    /***
     *
     * @param service  object or class
     * @return
     */
    String getServiceName(Object service);
}
