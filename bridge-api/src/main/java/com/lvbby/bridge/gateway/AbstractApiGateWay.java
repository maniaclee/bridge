package com.lvbby.bridge.gateway;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ServiceNameExtractor;
import com.lvbby.bridge.api.impl.ClassNameServiceNameExtractor;
import com.lvbby.bridge.gateway.*;

import java.util.List;

/**
 * Created by lipeng on 16/10/19.
 */
public abstract class AbstractApiGateWay implements ApiGateWay {
    protected List<ApiGateWayPreHandler> preHandlers = Lists.newLinkedList();
    protected List<ApiGateWayPostHandler> postHandlers = Lists.newArrayList();
    protected List<ApiGateWayFilter> apiGateWayFilters = Lists.newLinkedList();
    protected ServiceNameExtractor serviceNameExtractor = new ClassNameServiceNameExtractor();

    protected List<ErrorHandler> errorHandlers = Lists.newLinkedList();
    protected ErrorHandler errorHandler;

    @Override
    public void addErrorHandler(ErrorHandler eh) {
        if (eh != null) {
            if (errorHandler == null) {
                errorHandler = eh;
                return;
            }
            getTailErrorHanlder(errorHandler).setNextErrorHandler(eh);
        }
    }

    private ErrorHandler getTailErrorHanlder(ErrorHandler errorHandler) {
        while (errorHandler.getNextErrorHandler() != null)
            errorHandler = errorHandler.getNextErrorHandler();
        return errorHandler;
    }

    @Override
    public void addApiFilter(ApiGateWayFilter apiGateWayFilter) {
        if (apiGateWayFilter != null)
            apiGateWayFilters.add(apiGateWayFilter);
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
