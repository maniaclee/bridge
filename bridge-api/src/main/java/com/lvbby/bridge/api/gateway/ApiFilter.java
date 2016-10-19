package com.lvbby.bridge.api.gateway;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiFilter {

    boolean canVisit(Context context);
}
