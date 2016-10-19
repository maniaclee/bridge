package com.lvbby.bridge.filter;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Request;

/**
 * Created by lipeng on 16/10/19.
 */
public class BlacklistApiGateWayFilter implements ApiGateWayFilter {
    private Multimap<String, String> map = LinkedListMultimap.create();

    public void blockApi(String service, String method) {
        map.put(service, method);
    }

    @Override
    public boolean canVisit(Request request) {
        return map.get(request.getServiceName()).contains(request.getMethod());
    }
}
