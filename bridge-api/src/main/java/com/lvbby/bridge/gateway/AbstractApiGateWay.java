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
    protected List<ApiGateWayInitHandler> initHandlers = Lists.newLinkedList();
    protected ServiceNameExtractor serviceNameExtractor = new ClassNameServiceNameExtractor();

    protected ErrorHandler errorHandler;

    @Override
    public void addErrorHandler(ErrorHandler eh) {
        if (eh != null) {
            if (errorHandler == null) {
                errorHandler = eh;
                return;
            }
            if (!checkTypeExisted(errorHandler, eh)) {
                getTailErrorHandler(errorHandler).setNextErrorHandler(eh);
            }
        }
    }

    private ErrorHandler getTailErrorHandler(ErrorHandler errorHandler) {
        while (errorHandler.getNextErrorHandler() != null)
            errorHandler = errorHandler.getNextErrorHandler();
        return errorHandler;
    }

    @Override
    public void addApiFilter(ApiGateWayFilter apiGateWayFilter) {
        if (apiGateWayFilter != null && !checkTypeExisted(filters, apiGateWayFilter))
            filters.add(apiGateWayFilter);
    }


    @Override
    public void addPreHandler(ApiGateWayPreHandler apiGateWayPreHandler) {
        if (apiGateWayPreHandler != null && !checkTypeExisted(preHandlers, apiGateWayPreHandler))
            preHandlers.add(apiGateWayPreHandler);
    }

    @Override
    public void addInitHandler(ApiGateWayInitHandler apiGateWayInitHandler) {
        if (apiGateWayInitHandler != null && !checkTypeExisted(initHandlers, apiGateWayInitHandler))
            initHandlers.add(apiGateWayInitHandler);
    }

    @Override
    public void addPostHandler(ApiGateWayPostHandler apiGateWayPostHandler) {
        postHandlers.add(apiGateWayPostHandler);
    }

    private boolean checkTypeExisted(List list, Object object) {
        return list.stream().anyMatch(o -> o.getClass().equals(object.getClass()));
    }

    private boolean checkTypeExisted(ErrorHandler src, ErrorHandler eh) {
        if (src == null || eh == null)
            return false;
        while (src != null) {
            if (src.getClass().equals(eh.getClass()))
                return true;
            src = src.getNextErrorHandler();
        }
        return false;
    }

    public ServiceNameExtractor getServiceNameExtractor() {
        return serviceNameExtractor;
    }

    public void setServiceNameExtractor(ServiceNameExtractor serviceNameExtractor) {
        this.serviceNameExtractor = serviceNameExtractor;
    }

    @Override
    public List<ApiGateWayPreHandler> getPreHandlers() {
        return preHandlers;
    }

    @Override
    public List<ApiGateWayPostHandler> getPostHandlers() {
        return postHandlers;
    }

    @Override
    public List<ApiGateWayFilter> getFilters() {
        return filters;
    }

    @Override
    public List<ApiGateWayInitHandler> getInitHandlers() {
        return initHandlers;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }
}
