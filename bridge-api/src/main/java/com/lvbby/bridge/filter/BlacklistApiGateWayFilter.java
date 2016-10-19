package com.lvbby.bridge.filter;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.gateway.ApiGateWayFilter;
import com.lvbby.bridge.gateway.Context;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lipeng on 16/10/19.
 * a black list filter for blocking api service by service name & method name
 */
public class BlacklistApiGateWayFilter implements ApiGateWayFilter {
    private static final String INNER_VALUE = "-1";
    private Multimap<String, String> map = LinkedListMultimap.create();

    public void addApi(String service, String method) {
        if (StringUtils.isBlank(service))
            return;
        if (StringUtils.isBlank(method))
            method = INNER_VALUE;
        map.put(service, method);
    }

    @Override
    public boolean canVisit(Context request) {
        String serviceName = request.getRequest().getServiceName();
        if (!map.containsKey(serviceName))
            return true;
        return !map.containsEntry(serviceName, INNER_VALUE) && !map.containsEntry(serviceName, request.getRequest().getMethod());
    }
}
