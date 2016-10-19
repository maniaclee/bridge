package com.lvbby.bridge.gateway;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiGateWayFilter {

    boolean canVisit(Context request);
}
