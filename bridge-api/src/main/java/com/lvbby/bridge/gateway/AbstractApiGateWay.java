package com.lvbby.bridge.gateway;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ServiceNameExtractor;
import com.lvbby.bridge.api.impl.ClassNameServiceNameExtractor;

import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 */
public abstract class AbstractApiGateWay implements ApiGateWay {
    protected List<ApiGateWayPreHandler> preHandlers = Lists.newLinkedList();
    protected List<ApiGateWayPostHandler> postHandlers = Lists.newArrayList();
    protected List<ApiGateWayFilter> filters = Lists.newLinkedList();
    protected ServiceNameExtractor serviceNameExtractor = new ClassNameServiceNameExtractor();

    protected ErrorHandler errorHandler;

    @Override
    public void addErrorHandler(ErrorHandler eh) {
        if (eh != null) {
            if (errorHandler == null) {
                errorHandler = eh;
                return;
            }
            getTailErrorHandler(errorHandler).setNextErrorHandler(eh);
        }
    }

    private ErrorHandler getTailErrorHandler(ErrorHandler errorHandler) {
        while (errorHandler.getNextErrorHandler() != null)
            errorHandler = errorHandler.getNextErrorHandler();
        return errorHandler;
    }

    @Override
    public void addApiFilter(ApiGateWayFilter apiGateWayFilter) {
        if (apiGateWayFilter != null)
            filters.add(apiGateWayFilter);
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

    public List<ApiGateWayPreHandler> getPreHandlers() {
        return preHandlers;
    }

    public List<ApiGateWayPostHandler> getPostHandlers() {
        return postHandlers;
    }

    public List<ApiGateWayFilter> getFilters() {
        return filters;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }
}
