package com.lvbby.bridge.http;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.gateway.*;
import com.lvbby.bridge.http.filter.anno.HttpAttributeAnnotationValidateFilter;
import com.lvbby.bridge.http.filter.anno.HttpMethodFilter;
import com.lvbby.bridge.http.filter.anno.HttpUserFilter;
import com.lvbby.bridge.http.handler.HttpParameterParsingInitHandler;
import com.lvbby.bridge.http.handler.HttpSessionClearPostHandler;
import com.lvbby.bridge.http.handler.HttpSessionSavePostHandler;
import com.lvbby.bridge.http.handler.HttpUserIdInjectInitHandler;
import com.lvbby.bridge.http.parser.HttpApiRequestAttributeParser;
import com.lvbby.bridge.http.parser.HttpApiRequestParser;
import com.lvbby.bridge.http.parser.HttpApiRequestPathParser;
import com.lvbby.bridge.http.tool.HttpLoginHandler;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by peng on 16/9/24.
 * usage above the ApiGateWay
 */
public class HttpBridge {
    private ApiGateWay apiGateWay;
    private HttpApiRequestParser httpApiRequestParser = new HttpApiRequestAttributeParser();
    public static final String EXT_HTTP_REQUEST = "EXT_HTTP_REQUEST";
    public static final String EXT_HTTP_RESPONSE = "EXT_HTTP_RESPONSE";
    public static final String EXT_HTTP_BRIDGE = "EXT_HTTP_BRIDGE";
    private HttpUserManager httpUserManager;
    private boolean initialized = false;

    public static HttpBridge of(ApiGateWay apiGateWay) {
        HttpBridge httpBridge = new HttpBridge();
        return httpBridge.init(apiGateWay);
    }

    public static HttpBridge empty() {
        return new HttpBridge();
    }

    public HttpBridge init(ApiGateWay apiGateWay) {
        this.apiGateWay = apiGateWay;
        init();
        return this;
    }

    private void init() {
        if (initialized)
            return;
        Validate.notNull(apiGateWay);
        /** add default filters */
        this.apiGateWay.addApiFilter(new HttpMethodFilter());
        this.apiGateWay.addApiFilter(new HttpAttributeAnnotationValidateFilter());
        if (httpUserManager != null) {
            apiGateWay.addApiFilter(HttpUserFilter.of(httpUserManager));
        }

        /** param convert */
        this.apiGateWay.addInitHandler(new HttpParameterParsingInitHandler());
        /** user id inject */
        this.apiGateWay.addInitHandler(new HttpUserIdInjectInitHandler());

        /** inject */
        this.apiGateWay.addInitHandler(paramParsingContext -> {
            for (MethodParameter methodParameter : paramParsingContext.getApiMethod().getParamTypes()) {
                if (methodParameter.getType().isAssignableFrom(HttpServletRequest.class)) {
                    apiGateWay.paramsParser(paramParsingContext.getRequest()).addParameter(paramParsingContext, methodParameter, paramParsingContext.getRequest().getAttribute(EXT_HTTP_REQUEST));
                }
                if (methodParameter.getType().isAssignableFrom(HttpServletResponse.class)) {
                    apiGateWay.paramsParser(paramParsingContext.getRequest()).addParameter(paramParsingContext, methodParameter, paramParsingContext.getRequest().getAttribute(EXT_HTTP_RESPONSE));
                }
            }
        });
        /** add default post handlers */
        _addPostHandlerFirst(new HttpSessionSavePostHandler());
        _addPostHandlerFirst(new HttpSessionClearPostHandler());

        /** add login function */
        _addPreHandlerFirst(new HttpLoginHandler());
        _addPostHandlerFirst(new HttpLoginHandler());
        initialized = true;
    }

    public HttpBridge enableUrlPathParsing(String path) {
        this.httpApiRequestParser = HttpApiRequestPathParser.of(path);
        return this;
    }

    /***
     * add the system post handler to the first
     *
     * @param apiGateWayPostHandler
     */
    private void _addPostHandlerFirst(ApiGateWayPostHandler apiGateWayPostHandler) {
        ApiGateWay apiGateWay = getApiGateWay();
        if (apiGateWay instanceof AbstractApiGateWay) {
            ((AbstractApiGateWay) apiGateWay).getPostHandlers().add(0, apiGateWayPostHandler);
        }
    }

    private void _addPreHandlerFirst(ApiGateWayPreHandler apiGateWayPreHandler) {
        ApiGateWay apiGateWay = getApiGateWay();
        if (apiGateWay instanceof AbstractApiGateWay) {
            ((AbstractApiGateWay) apiGateWay).getPreHandlers().add(0, apiGateWayPreHandler);
        }
    }


    /***
     * write the response back to the response as json
     *
     * @param request
     * @param response
     */
    public void processBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object re = process(request, response);
            if (re == null)
                return;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(re));
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Request req = httpApiRequestParser.parse(request);

        //add ext params
        req.addAttribute(EXT_HTTP_REQUEST, request)
                .addAttribute(EXT_HTTP_RESPONSE, response)
                .addAttribute(EXT_HTTP_BRIDGE, this);
        return apiGateWay.proxy(req);
    }


    public ApiGateWay getApiGateWay() {
        return apiGateWay;
    }

    public void setApiGateWay(ApiGateWay apiGateWay) {
        this.apiGateWay = apiGateWay;
    }

    public HttpApiRequestParser getHttpApiRequestParser() {
        return httpApiRequestParser;
    }

    public void setHttpApiRequestParser(HttpApiRequestParser httpApiRequestParser) {
        this.httpApiRequestParser = httpApiRequestParser;
    }

    public HttpUserManager getHttpUserManager() {
        return httpUserManager;
    }

    public HttpBridge httpUserManager(HttpUserManager httpUserManager) {
        this.httpUserManager = httpUserManager;
        return this;
    }
}
