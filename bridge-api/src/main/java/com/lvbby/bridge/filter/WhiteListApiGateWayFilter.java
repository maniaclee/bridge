package com.lvbby.bridge.filter;

import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 */
public class WhiteListApiGateWayFilter extends BlacklistApiGateWayFilter {
    @Override
    public boolean canVisit(Context request) {
        return !super.canVisit(request);
    }
}
