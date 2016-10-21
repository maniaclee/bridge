package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/10/20.
 */
public interface ParamsBuilder {
    Params of(Object arg);
    String getType();
}
