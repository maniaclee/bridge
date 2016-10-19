package com.lvbby.bridge.gateway.impl;

import com.google.common.collect.Lists;
import com.lvbby.bridge.gateway.*;

import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 */
public abstract class AbstractApiGateWay implements ApiGateWay {
    protected List<ApiGateWayPreHandler> preHandlers = Lists.newLinkedList();
    protected List<ApiGateWayPostHandler> postHandlers = Lists.newArrayList();
    protected List<ApiFilter> apiFilters = Lists.newLinkedList();
    protected ServiceNameExtractor serviceNameExtractor = new ClassNameServiceNameExtractor();


    @Override
    public void addApiFilter(ApiFilter apiFilter) {
        if (apiFilter != null)
            apiFilters.add(apiFilter);
    }

    @Override
    public void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler) {
        if (apiGateWayPreHandler != null)
            preHandlers.add(apiGateWayPreHandler);
    }

    @Override
    public void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler) {
        postHandlers.add(apiGateWayPostHandler);
    }

    public ServiceNameExtractor getServiceNameExtractor() {
        return serviceNameExtractor;
    }

    public void setServiceNameExtractor(ServiceNameExtractor serviceNameExtractor) {
        this.serviceNameExtractor = serviceNameExtractor;
    }
}
